import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models';
import { LoginService } from 'src/app/services/logInOut.service';
import { SuccessComponent } from './success.component';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form!: FormGroup;
  email = new FormControl<string>('', [Validators.email, Validators.required])
  hide = true;
  userAlrExists = false;
  createErrorMsg = "";

  constructor(private fb: FormBuilder, private loginSvc: LoginService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm() {
    return this.fb.group({
      username: this.fb.control<string>('', [Validators.required]),
      email: this.email,
      password: this.fb.control<string>('', [Validators.required])
    })
  }

  getErrorMessage() {
    return this.email.hasError('email') ? 'Not a valid email' :
      "";
  }

  processForm() {
    console.info(">>>>> processing new sign up");
    const user: User = this.form.value as User;
    console.info("username: " + user.username);
    this.loginSvc.createUser(user)
      .then(response => {
        this.userAlrExists = false;
        console.info("response: " + response.body)
        this.openDialog();
        this.form.reset();
      })
      .catch(error => {
        console.info(error)
        this.userAlrExists = true;
        this.createErrorMsg = error.error;
      })

  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.height = '300px';
    dialogConfig.width = '800px'
    dialogConfig.hasBackdrop = true;

    this.dialog.open(SuccessComponent, dialogConfig);
}

}
