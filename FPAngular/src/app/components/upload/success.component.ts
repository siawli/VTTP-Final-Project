import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-upload-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class UploadSuccessComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<UploadSuccessComponent>,
    private route: Router) { }

  ngOnInit(): void {
  }

  toExplore() {
    this.dialogRef.close();
    this.route.navigate(['/masterKitchen/explore'])
  }

  toNewPost() {
    this.dialogRef.close();
    this.route.navigate(['/masterKitchen/upload/snap'])
  }

}
