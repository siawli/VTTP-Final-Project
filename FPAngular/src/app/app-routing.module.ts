import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExploreComponent } from './components/explore/explore.component';
import { LandingComponent } from './components/login/landing.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/login/register.component';
import { AuthorizeGuard } from './services/authorizeguard.service';

const routes: Routes = [
  {path: '', component: LandingComponent},
  {path: 'signUp', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'masterKitchen/:username/explore', component: ExploreComponent, canActivate: [AuthorizeGuard]
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
