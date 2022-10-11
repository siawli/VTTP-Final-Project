import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SavedRecipesComponent } from './components/profile/saved-recipes.component';
import { MyRecipesComponent } from './components/profile/my-recipes.component';
import { FindRecipesComponent } from './components/search/find-recipes.component';
import { ListRecipesComponent } from './components/search/list-recipes.component';
import { RecipeDetailsComponent } from './components/search/recipe-details.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/login/register.component';
import { NavbarComponent } from './components/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { LandingComponent } from './components/login/landing.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { LoginService } from './services/logInOut.service';
import { ExploreComponent } from './components/explore/explore.component';
import { JWTTokenService } from './services/jwt.service';
import { AppCookieService } from './services/cookie.service';
import { AuthorizeGuard } from './services/authorizeguard.service';
import { UniversalAppInterceptor } from './services/httpinterceptor.service';
import { SuccessComponent } from './components/login/success.component';
import { UploadComponent } from './components/upload/upload.component';
import { RecipeService } from './services/recipe.services';

@NgModule({
  declarations: [
    AppComponent,
    SavedRecipesComponent,
    MyRecipesComponent,
    FindRecipesComponent,
    ListRecipesComponent,
    RecipeDetailsComponent,
    LoginComponent,
    RegisterComponent,
    NavbarComponent,
    LandingComponent,
    ExploreComponent,
    SuccessComponent,
    UploadComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [LoginService, JWTTokenService, 
    AppCookieService, AuthorizeGuard, RecipeService,
    { provide: HTTP_INTERCEPTORS, useClass: UniversalAppInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
