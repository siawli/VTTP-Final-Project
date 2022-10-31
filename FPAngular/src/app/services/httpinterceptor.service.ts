import { Injectable, Inject, Optional, OnInit, Input } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpEvent } from '@angular/common/http';
import { LoginService } from './logInOut.service';
import { catchError, Observable, of, Subscription } from 'rxjs';
import { AppCookieService } from './cookie.service';
import { Router } from '@angular/router';

@Injectable()
export class UniversalAppInterceptor implements HttpInterceptor {

    constructor(private loginSvc: LoginService,
                private cookieSvc: AppCookieService,
                private router: Router) { }

    subAuthenticate$!: Subscription
    private _jwtToken!: string

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
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
        return next.handle(req).pipe(
            catchError(
                (error) => {
                    if (error.status === 401) {
                        this.handleAuthError();
                        return of(error)
                    }
                    throw error
                }
            )
        );
    }

    private handleAuthError() {
        this.cookieSvc.remove("email");
        this.cookieSvc.remove("username");
        this.cookieSvc.remove("token");
        this.router.navigate(['/login']);
      }
}