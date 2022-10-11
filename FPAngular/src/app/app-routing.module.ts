import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExploreComponent } from './components/explore/explore.component';
import { LandingComponent } from './components/login/landing.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/login/register.component';
import { FindRecipesComponent } from './components/search/find-recipes.component';
import { ListRecipesComponent } from './components/search/list-recipes.component';
import { RecipeDetailsComponent } from './components/search/recipe-details.component';
import { UploadComponent } from './components/upload/upload.component';
import { AuthorizeGuard } from './services/authorizeguard.service';

const routes: Routes = [
  {path: '', component: LandingComponent},
  {path: 'signUp', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'masterKitchen/explore', component: ExploreComponent, canActivate: [AuthorizeGuard]},
  {path: 'masterKitchen/upload', component: UploadComponent, canActivate: [AuthorizeGuard]},
  {
    path: 'masterKitchen/search',
    component: FindRecipesComponent,
    children: [
      {
        path: '',
        component: ListRecipesComponent,
        canActivate: [AuthorizeGuard]
      },
      {
        path: ':query/:num',
        component: ListRecipesComponent,
        canActivate: [AuthorizeGuard]
      },
      {
        path: ':query/:num/:id',
        component: RecipeDetailsComponent,
        canActivate: [AuthorizeGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
