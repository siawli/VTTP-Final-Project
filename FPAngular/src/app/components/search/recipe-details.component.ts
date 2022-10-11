import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models';
import { RecipeService } from 'src/app/services/recipe.services';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})
export class RecipeDetailsComponent implements OnInit {

  constructor(private ar: ActivatedRoute, private recipeSvc: RecipeService) { }

  query!: string
  querySub$!: Subscription

  id!: string
  recipeIdSub$!: Subscription

  num!: string
  numSub$!: Subscription

  recipe!: Recipe

  ngOnInit(): void {
    if (this.ar.snapshot.params['query']) {
      this.query = this.ar.snapshot.params['query']
      this.querySub$ = this.ar.params.subscribe(v => {
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.query = v.query
      })
    }
    if (this.ar.snapshot.params['id']) {
      this.id = this.ar.snapshot.params['id']
      this.recipeIdSub$ = this.ar.params.subscribe(b => {
        console.info('>subscribe: ', b)
        // @ts-ignore
        this.recipeId = b.id
      })
    }
    if (this.ar.snapshot.params['num']) {
      this.num = this.ar.snapshot.params['num']
      this.numSub$ = this.ar.params.subscribe(n => {
        console.info('>subscribe: ', n)
        // @ts-ignore
        this.num = b.num
      })
    }
    console.info(">>>> this.recipeId: " + this.id);

    this.recipeSvc.getRecipeDetails(this.id)
      .then(result => this.recipe = result)
      .catch(error => console.info("error in get recipe details: " + error))
  }

}
