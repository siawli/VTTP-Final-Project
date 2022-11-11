import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SavedRecipe } from 'src/app/models';
import { SavedRecipesService } from 'src/app/services/savedrecipes.service';

@Component({
  selector: 'app-saved-recipes',
  templateUrl: './saved-recipes.component.html',
  styleUrls: ['./saved-recipes.component.css']
})
export class SavedRecipesComponent implements OnInit {

  constructor(private savedRecipesSvc: SavedRecipesService,
              private route: Router) { }

  savedRecipes: SavedRecipe[] = []
  noRecipesSaved = false

  ngOnInit(): void {
    this.savedRecipesSvc.getAllSavedRecipes()
      .then(result => {
        console.info("result of getting saved recipes: " + result)
        this.savedRecipes = result;
        console.info("savedRecipes length: " + this.savedRecipes.length)
      })
      .catch(error => {
        this.noRecipesSaved = true;
        console.info(">>>> failed to obtain saved recipes: " + error)
      })
  }

  getRecipeDetails(recipe_id: string) {
    this.savedRecipesSvc.setRecipeId(recipe_id);
    this.route.navigate(['/masterKitchen/search/' + recipe_id])
  }


}
