import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";

@Injectable()
export class PostService {

    constructor(private http: HttpClient) {}

    createPost(file: File | Blob, name:string, email:string, phone:string, title:string, description:string) {
        const data = new FormData
        data.set('name', name)
        data.set('email', email)
        data.set('phone', phone)
        data.set('title', title)
        data.set('description', description)
        data.set('file', file)

        return firstValueFrom(this.http.post<any>('/api/posting', data))
    }

    confirmPost(postingId: string) {
        return firstValueFrom(this.http.put<any>(`/api/posting/${postingId}`,postingId))
    }
}