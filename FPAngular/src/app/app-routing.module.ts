import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExploreComponent } from './components/explore/explore.component';
import { IdComponent } from './components/explore/id.component';
import { LatestComponent } from './components/explore/latest.component';
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
  {path: '', component: LandingComponent},
  {path: 'signUp', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {
    path: 'masterKitchen/explore',
    component: ExploreComponent,
    canActivate: [AuthorizeGuard],
    children: [
      {
        path: 'byRecipeId',
        component: IdComponent,
        canActivate: [AuthorizeGuard],
        children: [
          {
            path: ':id',
            component: LatestComponent,
            canActivate: [AuthorizeGuard]
          }
        ]
      },
      {
        path: ':page',
        component: LatestComponent,
        canActivate: [AuthorizeGuard]
      },
    ]},
  
  {path: 'masterKitchen/upload/snap', component: SnapComponent, canActivate: [AuthorizeGuard]},
  {path: 'masterKitchen/upload/form', component: FormComponent, canActivate: [AuthorizeGuard]},
  {
    path: 'masterKitchen/search',
    component: FindRecipesComponent,
    children: [
      // {
      //   path: '',
      //   component: ListRecipesComponent,
      //   canActivate: [AuthorizeGuard]
      // },
      {
        path: ':query/:num',
        component: ListRecipesComponent,
        canActivate: [AuthorizeGuard]
      }
    ]
  },
  {path: 'masterKitchen/search/:query/:num/:id', component: RecipeDetailsComponent, canActivate: [AuthorizeGuard]},
  {path: 'masterKitchen/search/:id', component: RecipeDetailsComponent, canActivate: [AuthorizeGuard]},
  {
    path: 'masterKitchen/profile',
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
        component: LatestComponent,
        canActivate: [AuthorizeGuard]
      }
    ]}
  
  // {path: 'masterKitchen/explore/popular', component: LatestComponent, canActivate: [AuthorizeGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
