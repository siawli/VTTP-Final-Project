import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-unsuccessful',
  templateUrl: './unsuccessful.component.html',
  styleUrls: ['./unsuccessful.component.css']
})
export class UnsuccessfulComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<UnsuccessfulComponent>,
    private route: Router) { }

  ngOnInit(): void {
  }

  toExplore() {
    this.dialogRef.close();
    this.route.navigate(['/masterKitchen/explore/latest'])
  }

  toNewPost() {
    this.dialogRef.close();
    this.route.navigate(['/masterKitchen/upload/snap'])
  }
}
