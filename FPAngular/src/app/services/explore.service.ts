import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";

@Injectable()
export class ExploreService {

    constructor(private httpClient: HttpClient) { }

    getAllPost() {
        return firstValueFrom(
            this.httpClient.get<any>("/explore/all")
        )
    }

    getImageFromS3(uuid: string) {
        const headers = new HttpHeaders()
            .set('Accept', "image/png")
        return firstValueFrom(
            this.httpClient.get<any>(`/amazonS3/${uuid}`, { headers: headers, responseType: 'blob' as 'json' })
        )
    }
}
// const reader = new FileReader();
// reader.readAsDataURL(data); //FileStream response from .NET core backend
// reader.onload = _event => {
//     url = reader.result; //url declared earlier
//     image.nativeElement.src = url; //image declared earlier
// };