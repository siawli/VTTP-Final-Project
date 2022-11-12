import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExploreComponent } from './components/explore/explore.component';
import { IdComponent } from './components/explore/id.component';
import { PostComponent } from './components/explore/post.component';
import { LandingComponent } from './components/login/landing.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/login/register.component';
import { SavedRecipesComponent } from './components/profile/saved-recipes.component';
import { FindRecipesComponent } from './components/search/find-recipes.component';
import { ListRecipesComponent } from './components/search/list-recipes.component';
import { RecipeDetailsComponent } from './components/search/recipe-details.component';
import { FormComponent } from './components/upload/form.component';
import { SnapComponent } from './components/upload/snap.component';
import { AuthorizeGuard } from './services/authorizeguard.service';

const routes: Routes = [
  { path: '', component: LandingComponent, title: 'Welcome'},
  { path: 'signUp', component: RegisterComponent, title: 'Sign Up' },
  { path: 'login', component: LoginComponent, title: 'Login'},
  {
    path: 'masterKitchen/explore',
    component: ExploreComponent,
    title: 'Explore',
    canActivate: [AuthorizeGuard],
    children: [
      {
        path: 'byRecipeId',
        component: IdComponent,
        canActivate: [AuthorizeGuard],
        children: [
          {
            path: ':id',
            component: PostComponent,
            canActivate: [AuthorizeGuard]
          }
        ]
      },
      {
        path: ':page',
        component: PostComponent,
        canActivate: [AuthorizeGuard]
      },
    ]
  },

  { path: 'masterKitchen/upload/snap', component: SnapComponent, canActivate: [AuthorizeGuard], title: 'Snap post'},
  { path: 'masterKitchen/upload/snap/:id', component: SnapComponent, canActivate: [AuthorizeGuard], title: 'Snap post' },
  { path: 'masterKitchen/upload/form', component: FormComponent, canActivate: [AuthorizeGuard], title: 'Upload post' },
  { path: 'masterKitchen/upload/form/:id', component: FormComponent, canActivate: [AuthorizeGuard], title: 'Upload post' },
  {
    path: 'masterKitchen/search',
    component: FindRecipesComponent,
    title: 'Search Recipes',
    children: [
      {
        path: ':query/:num',
        component: ListRecipesComponent,
        canActivate: [AuthorizeGuard]
      }
    ]
  },
  { path: 'masterKitchen/search/:query/:num/:id', component: RecipeDetailsComponent, canActivate: [AuthorizeGuard], title: 'Recipe Details'},
  { path: 'masterKitchen/search/:id', component: RecipeDetailsComponent, canActivate: [AuthorizeGuard],  title: 'Recipe Details' },
  {
    path: 'masterKitchen/profile',
    title: 'My Profile',
    component: ExploreComponent,
    canActivate: [AuthorizeGuard],
    children: [
      {
        path: 'savedRecipes',
        component: SavedRecipesComponent,
        canActivate: [AuthorizeGuard]
      },
      {
        path: ':page',
        component: PostComponent,
        canActivate: [AuthorizeGuard]
      }
    ]
  },
  {path: "**", redirectTo: '/', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
