import { Component } from '@angular/core';
import { HostListener } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'FPAngular';

  loggedIn = false;


@HostListener('window:scroll', ['$event'])

onWindowScroll() {
    let element = document.querySelector('.nav') as HTMLElement;
    if (window.pageYOffset > 5) {
      element.classList.add('navbar-inverse');
    } else {
      element.classList.remove('navbar-inverse');
    }
  }
}
