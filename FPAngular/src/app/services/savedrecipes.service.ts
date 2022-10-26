import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { AppCookieService } from "./cookie.service";

@Injectable()
export class SavedRecipesService {

    constructor(private httpClient: HttpClient,
                private cookieSvc: AppCookieService) { }
            
    email = this.cookieSvc.get("email")
    _recipeId!: string

    setRecipeId(recipeId: string) {
        this._recipeId = recipeId
    }
    getRecipeId() {
        return this._recipeId
    }

    alterSavedRecipes(recipe_id: string, recipe_label: string, alteration: string) {
        const params = new HttpParams()
            .set("email", this.email)
            .set("recipe_label", recipe_label)
            .set("recipe_id", recipe_id)
            .set("alteration", alteration)

        return firstValueFrom(
            this.httpClient.get<any>('/saved/alterSaved', {params})
        )
    }

    getAllSavedRecipes() {
        const params = new HttpParams()
            .set("email", this.email)
        
        return firstValueFrom(
            this.httpClient.get<any>("/saved/allRecipes", {params})
        )
    }
}