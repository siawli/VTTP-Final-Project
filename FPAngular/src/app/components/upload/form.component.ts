import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Post } from 'src/app/models';
import { UploadService } from 'src/app/services/upload.service';
import { formatDate } from '@angular/common'
import { v4 as uuid } from 'uuid';
import { AppCookieService } from 'src/app/services/cookie.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { UploadSuccessComponent } from './success.component';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  imageData!: string
  imageWidth!: string
  form!: FormGroup
  placeholder = "Getting recipe label..."
  recipe_id_control = this.fb.control<string>('', [Validators.required, Validators.minLength(32)])

  constructor(private router: Router, private uploadSvc: UploadService,
              private fb: FormBuilder, private cookieSvc: AppCookieService,
              private dialog: MatDialog) { }

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
      recipe_id: this.recipe_id_control,
      recipe_label: [{value: 'Getting recipe label...', disabled: true}],
      // recipe_label: this.fb.control<string>('', [Validators.required]),
      ratings: this.fb.control<number>(1, [Validators.required])
    })
  }

  getLabel(event: any) {
    if (event.target.value.length === 32) {
      this.uploadSvc.getRecipeLabel(event.target.value)
        .then(result => {
          console.info("recipe_label: " + result)
          this.placeholder = result
        })
        .catch(error => {
          console.info("error in get recipe_label: " + error)
        })
    }
  }

  shareIt() {
    console.info('>>>> data: ', this.form.value)
    const post: Post = this.form.getRawValue() as Post;
    // console.info(">>>> title: " + post.title)
    // console.info(">>>> caption: " + post.caption)
    // console.info(">>>> rating: " + post.rating)
    post.date = formatDate(new Date(), 'yyyy-MM-dd', 'en')
    const imageUUID = uuid().toString().substring(0, 8);
    post.imageUUID = imageUUID;
    post.recipe_label = this.placeholder;
    post.username = this.cookieSvc.get("username")
    console.info(">>>> post recipe label: " + post.recipe_label)
    // console.info(">>>> imageUUID: " + imageUUID)
    post.email = this.cookieSvc.get("email")
    console.info("post.email: " + post.email)
    // console.info("date: " + post.date)
    this.uploadSvc.uploadPostSB(post)
      .then(result => {
        console.info("Success!")
        console.info("Result of uploadPostSB: " + result)
        this.uploadImgAmazon()
        this.openDialog();
        this.form.reset();
      })
      .catch(error => {
        console.info("error in uploadPostSB: " + error)
      })
    // console.info("imageDataToUrl: " + this.uploadSvc.dataUrl)
    // this.uploadSvc.uploadPost(post)
    //   .then(result => console.info(result))
    //   .catch(error => console.info(error))
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

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.height = '300px';
    dialogConfig.width = '800px'
    dialogConfig.hasBackdrop = true;

    this.dialog.open(UploadSuccessComponent, dialogConfig);
}

}
