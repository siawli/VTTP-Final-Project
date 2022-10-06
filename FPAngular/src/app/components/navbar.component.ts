import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { AppCookieService } from '../services/cookie.service';
import { LoginService } from '../services/logInOut.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private route: Router,
              private router: ActivatedRoute,
              private loginSvc: LoginService,
              private cookieSvc: AppCookieService) { }

  ngOnInit(): void {

  }

  loggedIn(): boolean {
    let url = window.location.href;
    if (!url.includes("masterKitchen")) {
      return false;
    } 
    return true;
  }

  logout() {
    this.cookieSvc.remove("token")
    this.cookieSvc.remove("username")
    this.cookieSvc.remove("email")
    this.route.navigate(['/']);
  }
    
  routeLandingPage() {
    this.route.navigate(['/']);
  }

}
