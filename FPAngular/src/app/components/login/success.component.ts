import { Component, OnInit } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import { Router } from '@angular/router';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<SuccessComponent>,
              private route: Router) { }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
    this.route.navigate(['/login'])
  }

}
