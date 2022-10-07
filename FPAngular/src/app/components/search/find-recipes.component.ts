import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-find-recipes',
  templateUrl: './find-recipes.component.html',
  styleUrls: ['./find-recipes.component.css']
})
export class FindRecipesComponent implements OnInit {

  constructor() { }

  disableRipple = true
  isRoundButton = false

  ngOnInit(): void {
  }

}
