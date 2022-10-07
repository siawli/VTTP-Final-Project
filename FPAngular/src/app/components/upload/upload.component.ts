import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {

  form!: FormGroup
  stepsArr!: FormArray

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm() {
    this.stepsArr = this.fb.array([])
    return this.fb.group({
      title: this.fb.control<string>('', [Validators.required]),
      description: this.fb.control<string>('', [Validators.required]),
      servings: this.fb.control<number>(1, [Validators.required]),
      steps: this.stepsArr,
      link: this.fb.control<string>('', [Validators.required]),
    })
  
  }

}
