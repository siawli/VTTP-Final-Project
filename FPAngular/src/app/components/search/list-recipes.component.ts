import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-list-recipes',
  templateUrl: './list-recipes.component.html',
  styleUrls: ['./list-recipes.component.css']
})
export class ListRecipesComponent implements OnInit {

  constructor(private recipeSvc: RecipeService,
    private route: Router,
    private ar: ActivatedRoute) { }

  linkSub$!: Subscription
  nextURL = ""
  noNext = true
  recipes: Recipe[] = []
  query!: string
  noPrev = true
  numPage!: number
  serviceCalledButError = false;

  ngOnInit(): void {
    if (this.ar.snapshot.params['query'] && this.ar.snapshot.params['num']) {
      this.query = this.ar.snapshot.params['query']
      this.numPage = this.ar.snapshot.params['num']
      this.linkSub$ = this.ar.params.subscribe(v => {
        this.recipes = []
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.query = v.query
        //@ts-ignore
        this.numPage = v.num
        if (this.numPage != 1) {
          this.noPrev = false
        }
        this.callGetRecipesSvc()
      })
    }
  }

  getRecipeDetails(recipe_id: string) {
    this.route.navigate(["/masterKitchen/search", this.query, this.numPage, recipe_id])
  }

  getNext(contValue: string) {
    this.recipes = [];
    this.numPage = Number(this.numPage) + 1
    this.route.navigate(["/masterKitchen/search", this.query, this.numPage])
    this.callGetRecipesSvc()
    this.noPrev = false;
    // KIV on contValue
  }

  getPrev() {
    this.numPage = Number(this.numPage) - 1;
    this.recipes = [];
    if (this.numPage == 1) {
      this.noPrev = true;
    }
    this.route.navigate(["/masterKitchen/search", this.query, this.numPage])
    this.callGetRecipesSvc();
  }

  callGetRecipesSvc() {
    this.recipeSvc.getRecipes(this.query, this.numPage, this.nextURL)
      .then(result => {
        this.nextURL = result.nextURL
        this.noNext = false
        this.recipes = result.recipes;
      })
      .catch(error => {
        this.serviceCalledButError = true
        this.noNext = true;
        console.info(">>>> error: " + error)
      })
  }
}
