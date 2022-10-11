import { Injectable, Inject, Optional, OnInit, Input } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { LoginService } from './logInOut.service';
import { Subscription } from 'rxjs';
import { AppCookieService } from './cookie.service';

@Injectable()
export class UniversalAppInterceptor implements HttpInterceptor {

    constructor(private loginSvc: LoginService,
                private cookieSvc: AppCookieService) { }

    subAuthenticate$!: Subscription
    private _jwtToken!: string

    intercept(req: HttpRequest<any>, next: HttpHandler) {
        if (req.headers.get("skip"))
            return next.handle(req);
        // console.info(">>>> in interceptor")

        this._jwtToken = this.cookieSvc.get("token")
        // console.info(">>>> interceptor jwtToken: " + this._jwtToken)
        req = req.clone({
            url: req.url,
            setHeaders: {
                Authorization: `Bearer ${this._jwtToken}`
            }
        });
        return next.handle(req);
    }
}