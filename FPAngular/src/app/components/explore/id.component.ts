import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-id',
  templateUrl: './id.component.html',
  styleUrls: ['./id.component.css']
})
export class IdComponent implements OnInit {

  constructor(private fb: FormBuilder,
              private route: Router) { }

  form!: FormGroup
  queryLink!: string

  ngOnInit(): void {
    const href = window.location.href.split("/")
    if (href.length > 7) {
      this.queryLink = href[7].split("%20").join(" ")
    }
    this.form = this.createForm()
  }

  createForm() {
    return this.fb.group({
      id: this.fb.control<string>(this.queryLink || '')
    })
  }

  processForm() {
    const id = this.form.get("id")?.value.trim()
    if (id.length > 0) {
      this.route.navigate(['/masterKitchen/explore/byRecipeId', id])
    }
  }
}
