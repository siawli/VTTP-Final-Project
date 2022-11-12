import { Component, OnInit, Output } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Subject, Subscription } from 'rxjs';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css']
})
export class ExploreComponent implements OnInit {

  constructor(private ar: ActivatedRoute,
              private route: Router) { }
              
  profileTab = false
  tab!: string
  activeLink!: string
  links!: [
    {
      label: string,
      path: string
    },
    {
      label: string,
      path: string
    },
    {
      label: string,
      path: string
    },
  ]

  @Output()
  changeTab = new Subject<String>();

  ngOnInit(): void {
    const page = window.location.href;

    if (page.includes("explore")) {
      this.links = [
        { label: 'Latest', path: '/masterKitchen/explore/latest' },
        { label: 'Popular', path: '/masterKitchen/explore/popular' },
        { label: 'Search by Recipe ID', path: '/masterKitchen/explore/byRecipeId' }
      ]
    } else if (page.includes("profile")) {
      this.links = [
        { label: 'My Posts', path: '/masterKitchen/profile/myPosts' },
        { label: 'Liked Posts', path: '/masterKitchen/profile/likedPosts' },
        { label: 'Saved Recipes', path: '/masterKitchen/profile/savedRecipes' }
      ]
    }
  }
}
