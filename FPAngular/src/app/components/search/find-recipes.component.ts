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
    this.form = this.createForm();
    if (this.ar.snapshot.params['query']) {
      this.queryLink = this.ar.snapshot.params['query']
      this.query$ = this.ar.params.subscribe(v => {
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.queryLink = v.query
      })
    }
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


