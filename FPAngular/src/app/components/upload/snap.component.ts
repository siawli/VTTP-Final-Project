import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WebcamImage } from 'ngx-webcam';
import { Subject } from 'rxjs';
import { UploadService } from 'src/app/services/upload.service';

@Component({
  selector: 'app-snap',
  templateUrl: './snap.component.html',
  styleUrls: ['./snap.component.css']
})
export class SnapComponent implements OnInit {

  trigger = new Subject<void>()
  triggerObs = this.trigger.asObservable()

  width = 500

  constructor(private router: Router, private uploadSvc: UploadService) { }

  ngOnInit(): void {
    this.width = Math.floor(window.innerWidth * .9)
  }

  snapshot(img: WebcamImage) {
    this.uploadSvc.dataUrl = img.imageAsDataUrl
    this.router.navigate(['/masterKitchen/upload/form'])
  }

  takePicture() {
    this.trigger.next()
  }

}
