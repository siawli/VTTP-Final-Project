import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs'; 
import { AppCookieService } from './cookie.service';
import { JWTTokenService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizeGuard implements CanActivate {
  constructor(private jwtService: JWTTokenService,
              private route: Router,
              private cookieSvc: AppCookieService) {
  }
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<any> | Promise<any> | boolean {
      if (!!this.cookieSvc.get("token")) {
          if (this.jwtService.isTokenExpired()) {
            // Should Redirect Sig-In Page
            return this.route.navigate(["/"])
          } else {
            return true;
          }
      } else {
            // Should Redirect Sign-In Page
            return this.route.navigate(["/"])
      }
  }
}