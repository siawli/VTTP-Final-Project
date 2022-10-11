import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UploadService } from 'src/app/services/upload.service';

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
    private fb: FormBuilder) { }

  ngOnInit(): void {
    if (!this.uploadSvc.dataUrl) {
      this.router.navigate(['/masterKitchen/upload/snap'])
      return
    }

    const w = Math.floor(window.innerWidth * .45)
    this.imageWidth = `${w}`

    this.imageData = this.uploadSvc.dataUrl

    this.form = this.fb.group({
      title: this.fb.control('', [Validators.required]),
      caption: this.fb.control('', [Validators.required]),
    })
  }

  shareIt() {
    console.info('>>>> data: ', this.form.value)
  }

}
