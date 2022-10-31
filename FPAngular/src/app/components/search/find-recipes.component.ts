import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-find-recipes',
  templateUrl: './find-recipes.component.html',
  styleUrls: ['./find-recipes.component.css']
})
export class FindRecipesComponent implements OnInit {

  form!: FormGroup;
  query!: string
  queryLink!: string
  query$!: Subscription

  constructor(private fb: FormBuilder,
              private recipeSvc: RecipeService,
              private route: Router,
              private ar: ActivatedRoute) { }

  ngOnInit(): void {
    const href = window.location.href.split("/")
    if (href.length > 6) {
      this.queryLink = href[6].split("%20").join(" ")
    }
    this.form = this.createForm();
  }

  createForm() {
    return this.fb.group({
      query: this.fb.control<string>(this.queryLink || '')
    })
  }

  processForm() {
    let query = this.form.get("query")?.value
    if (query.length > 0) {
      this.route.navigate(['/masterKitchen/search', query, 1])
    }
  }
}


