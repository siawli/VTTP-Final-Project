import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/logInOut.service';

@Component({
  selector: 'app-saved-recipes',
  templateUrl: './saved-recipes.component.html',
  styleUrls: ['./saved-recipes.component.css']
})
export class SavedRecipesComponent implements OnInit {

  constructor(private loginSvc: LoginService) { }

  ngOnInit(): void {

  }

}
