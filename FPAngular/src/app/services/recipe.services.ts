import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, Subject } from "rxjs";
import { Recipe, RecipeResponse } from "../models";

@Injectable()
export class RecipeService {

    constructor(private httpClient: HttpClient) { }

    getRecipeDetailsObs = new Subject<Recipe>();

    getRecipes(query: string, numPage: number) {
        const params = new HttpParams().set("query", query);
        console.info("query: " + query)

        return firstValueFrom(this.httpClient.get<RecipeResponse>(`/search/recipes/${numPage.toString()}`, {params}))
    }

    getRecipeDetails(id: string) {
        return firstValueFrom(this.httpClient.get<Recipe>(`/search/recipe/${id}`))
    }

}