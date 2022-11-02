import { AfterContentInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HostListener } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'FPAngular';

  constructor(private cdref: ChangeDetectorRef) {}

  loggedIn = false;
  navBarHeight!: number

  @HostListener('window:scroll', ['$event'])

  ngOnInit(): void {
      this.navBarHeight = 0;

  }

  ngAfterContentChecked() {
    this.cdref.detectChanges();
  }

  onWindowScroll() {
    let element = document.querySelector('.nav') as HTMLElement;
    if (window.pageYOffset > 5) {
      element.classList.add('navbar-inverse');
    } else {
      element.classList.remove('navbar-inverse');
    }
  }

  // adjustNavBar(event: number) {
  //   console.info(">>> event: " + event)
  //   this.navBarHeight = event + 60;
  //   console.info(">>> top: " + this.navBarHeight)
  // }
}
