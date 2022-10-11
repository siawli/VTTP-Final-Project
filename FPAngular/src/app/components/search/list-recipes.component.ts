import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models';
import { RecipeService } from 'src/app/services/recipe.services';

@Component({
  selector: 'app-list-recipes',
  templateUrl: './list-recipes.component.html',
  styleUrls: ['./list-recipes.component.css']
})
export class ListRecipesComponent implements OnInit {

  constructor(private recipeSvc: RecipeService,
    private route: Router,
    private ar: ActivatedRoute) { }

  recipeSub$!: Subscription
  querySub$!: Subscription
  nextURL = ""
  noNext = true
  recipes: Recipe[] = []
  query!: string
  prevURL = ""
  noPrev = true
  numPage = 1

  ngOnInit(): void {
    console.info('>>>> in ngOnInit = ', this.ar.snapshot.params['query'])
    if (this.ar.snapshot.params['query']) {
      this.query = this.ar.snapshot.params['query']
      this.querySub$ = this.ar.params.subscribe(v => {
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.query = v.query
      })
    }
    this.callGetRecipesSvc()
  }

  getRecipeDetails(recipe_id: string) {
    this.route.navigate(["/masterKitchen/search", this.query, this.numPage, recipe_id])
  }

  getNext() {
    this.numPage += 1
    this.prevURL = "first page"
  }

  callGetRecipesSvc() {
    this.recipeSvc.getRecipes(this.query)
    .then(result => {
      console.info(">>> result: " + result)
      this.nextURL = result.nextURL
      this.noNext = false
      this.recipes = result.recipes;
      console.info(">>>> recipesListLength: " + this.recipes.length)
      console.info(">>>> nextURL: " + this.nextURL)
    })
    .catch(error => {
      console.info(">>>> error: " + error)
    })
  }


}
