import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RecipeService } from 'src/app/services/recipe.services';

@Component({
  selector: 'app-find-recipes',
  templateUrl: './find-recipes.component.html',
  styleUrls: ['./find-recipes.component.css']
})
export class FindRecipesComponent implements OnInit {

  form!: FormGroup;
  query!: string

  constructor(private fb: FormBuilder,
              private recipeSvc: RecipeService,
              private route: Router) { }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm() {
    return this.fb.group({
      query: this.fb.control<string>('')
    })
  }

  processForm() {
    let query = this.form.get("query")?.value
    if (query.length > 0) {
      this.route.navigate(['/masterKitchen/search', query, 1])
    }
  }
}


