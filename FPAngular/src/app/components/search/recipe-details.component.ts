import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models';
import { RecipeService } from 'src/app/services/recipe.service';
import { SavedRecipesService } from 'src/app/services/savedrecipes.service';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})
export class RecipeDetailsComponent implements OnInit {

  constructor(private ar: ActivatedRoute,
              private recipeSvc: RecipeService,
              private savedRecipeSvc: SavedRecipesService,
              private route: Router,
              private location: Location) { }

  query!: string
  querySub$!: Subscription

  id!: string
  recipeIdSub$!: Subscription

  num!: string
  numSub$!: Subscription
  isSaved!: boolean;

  recipe: Recipe = {
    recipe_id: '',
    storedUUID: '',
    label: '',
    image: '',
    link: '',
    servings: 0,
    ingredientLines: [],
    calories: 0
  }

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
        this.num = n.num
      })
    }

    this.recipeSvc.getRecipeDetails(this.id)
      .then(result => {
        this.recipe = result.recipe
        this.isSaved = result.saved
      })
      .catch(error => console.info("error in get recipe details: " + error))
  }

  alterSavedRecipes(recipe_id: string, recipe_label: string, alteration: string) {
    console.info(">>>> recipe_label: " + recipe_label)
    this.savedRecipeSvc.alterSavedRecipes(recipe_id, recipe_label, alteration)
      .then(result => {
        console.info("result of saving recipe:" + result)
      })
      .catch()
    if (alteration.includes('add')) {
      this.isSaved = true;
    } else {
      this.isSaved = false;
    }
  }

  reRouteBack(){
    // [routerLink]="['/masterKitchen/search', query, num]"
    // if (!this.query && ! this.num) {
    //   this.route.navigate(['/masterKitchen/profile/savedRecipes'])
    // } else {
    //   this.route.navigate(['/masterKitchen/search', this.query, this.num])
    // }
    this.location.back()
  }

}
