import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { AppCookieService } from "./cookie.service";

@Injectable()
export class ExploreService {

    constructor(private httpClient: HttpClient,
                private cookieSvc: AppCookieService) { }

    getAllPost() {
        const params = new HttpParams()
                .set("email", this.cookieSvc.get("email"))
        return firstValueFrom(
            this.httpClient.get<any>("/explore/all", {params})
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
            .set("email", this.cookieSvc.get("email"))
            .set("post_id", post_id)
            .set("alteration", alteration)

        return firstValueFrom(
            this.httpClient.get<any>("/explore/updateLikes", {params}) 
        )
        // @GetMapping("/updateLikes/{post_id}/{alteration}")
    }
}