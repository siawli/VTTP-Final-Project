import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, Subject } from "rxjs";
import { Recipe, RecipeDetailsResponse, RecipeListResponse } from "../models";
import { AppCookieService } from "./cookie.service";

@Injectable()
export class RecipeService {

    constructor(private httpClient: HttpClient,
                private cookieSvc: AppCookieService) { }

    

    getRecipes(query: string, numPage: number, _contValue?: string) {
        let params;
        if (_contValue) {
            params = new HttpParams()
                .set("query", query)
                .set("_contValue", _contValue)
        } else {
            params = new HttpParams()
                    .set("query", query)
        }
        return firstValueFrom(this.httpClient.get<RecipeListResponse>(`/search/recipes/${numPage.toString()}`, {params}))
    }

    getRecipeDetails(id: string): Promise<RecipeDetailsResponse> {
        const params = new HttpParams()
            .set("email", this.cookieSvc.get("email"))
        return firstValueFrom(this.httpClient.get<RecipeDetailsResponse>(`/search/recipe/${id}`, {params}))
    }

}