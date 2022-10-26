import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { AppCookieService } from "./cookie.service";

@Injectable()
export class ExploreService {

    constructor(private httpClient: HttpClient,
                private cookieSvc: AppCookieService) { }

    email = this.cookieSvc.get("email")
    _params = new HttpParams().set("email", this.email)

    getPostsByRecipeId(recipe_id: string) {
        const params = new HttpParams()
                        .set("email", this.email)
                        .set("recipe_id", recipe_id)
        return firstValueFrom(
            this.httpClient.get<any>("/post/byRecipeId", {params})
        )
    }

    getMyPost() {
        const params = this._params;
        return firstValueFrom(
            this.httpClient.get<any>("/post/myPosts", {params})
        )
    }

    getAllLikedPost() {
        const params = this._params
        return firstValueFrom(
            this.httpClient.get<any>("/post/allLikedPosts", {params})
        )
    }

    getPopularPost() {
        const params = this._params
        return firstValueFrom(
            this.httpClient.get<any>("/post/popular", {params})
        )
    }

    getAllPost() {
        const params = this._params
        return firstValueFrom(
            this.httpClient.get<any>("/post/all", {params})
        )
    }

    getImageFromS3(uuid: string) {
        const headers = new HttpHeaders()
            .set('Accept', "image/png")
        return firstValueFrom(
            this.httpClient.get<any>(`/amazonS3/${uuid}`, { headers: headers, responseType: 'blob' as 'json' })
        )
    }

    updateLikesOnPost(post_id: number, alteration: string) {
        const params = new HttpParams()
            .set("email", this.email)
            .set("post_id", post_id)
            .set("alteration", alteration)

        return firstValueFrom(
            this.httpClient.get<any>("/post/updateLikes", {params}) 
        )
        // @GetMapping("/updateLikes/{post_id}/{alteration}")
    }
}