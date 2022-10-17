import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Post } from 'src/app/models';
import { UploadService } from 'src/app/services/upload.service';
import { formatDate } from '@angular/common'
import { v4 as uuid } from 'uuid';
import { AppCookieService } from 'src/app/services/cookie.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  imageData!: string
  imageWidth!: string
  form!: FormGroup

  constructor(private router: Router, private uploadSvc: UploadService,
              private fb: FormBuilder, private cookieSvc: AppCookieService) { }

  ngOnInit(): void {
    if (!this.uploadSvc.dataUrl) {
      this.router.navigate(['/masterKitchen/upload/snap'])
      return
    }

    const w = Math.floor(window.innerWidth * .45)
    this.imageWidth = `${w}`

    this.imageData = this.uploadSvc.dataUrl

    this.form = this.fb.group({
      title: this.fb.control<string>('', [Validators.required]),
      caption: this.fb.control<string>('', [Validators.required]),
      recipe_id: this.fb.control<string>('', [Validators.required]),
      ratings: this.fb.control<number>(1, [Validators.required])
    })
  }

  shareIt() {
    console.info('>>>> data: ', this.form.value)
    const post: Post = this.form.value as Post;
    // console.info(">>>> title: " + post.title)
    // console.info(">>>> caption: " + post.caption)
    // console.info(">>>> rating: " + post.rating)
    post.date = formatDate(new Date(), 'yyyy-MM-dd', 'en')
    const imageUUID = uuid().toString().substring(0, 8);
    post.imageUUID = imageUUID;
    // console.info(">>>> imageUUID: " + imageUUID)
    post.email = this.cookieSvc.get("email")
    console.info("post.email: " + post.email)
    // console.info("date: " + post.date)
    this.uploadSvc.uploadPostSB(post)
      .then(result => {
        console.info("Success!")
        console.info("Result of uploadPostSB: " + result)
        this.uploadImgAmazon()
      })
      // .catch(error => {
      //   console.info("error in uploadPostSB: " + error)
      // })
  //   console.info("imageDataToUrl: " + this.uploadSvc.dataUrl)
  //   this.uploadSvc.uploadPost(post)
  //     .then(result => console.info(result))
  //     .catch(error => console.info(error))
  }

  uploadImgAmazon() {
    this.uploadSvc.uploadPostAmazon()
      .then(result => {
        console.info("Result of uploadPostAmazon: " + result)
      })
      .catch(error => {
        console.info("error in uploadPostAmazon" + error)
      })
  }

}
