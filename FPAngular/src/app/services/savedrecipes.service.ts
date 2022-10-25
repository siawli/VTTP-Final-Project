import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { AppCookieService } from "./cookie.service";

@Injectable()
export class SavedRecipesService {

    constructor(private httpClient: HttpClient,
                private cookieSvc: AppCookieService) { }

    alterSavedRecipes(recipe_id: string, alteration: string) {
        const params = new HttpParams()
            .set("email", this.cookieSvc.get("email"))
            .set("recipe_id", recipe_id)
            .set("alteration", alteration)

        return firstValueFrom(
            this.httpClient.get<any>('/saved/alterSaved', {params})
        )
    }
}