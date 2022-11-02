import { Component, OnInit } from '@angular/core';
import { MatCarousel, MatCarouselComponent } from '@thouet/material-carousel';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  constructor() { }

  slides: string[] = [
    // '/assets/logo.png',
    '/assets/posts.png',
    '/assets/search.png',
    '/assets/recipe.png',
    '/assets/savedRecipes.png'
  ]

  ngOnInit(): void {
  }

}
