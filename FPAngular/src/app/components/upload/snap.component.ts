import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { WebcamImage } from 'ngx-webcam';
import { Subject, Subscription } from 'rxjs';
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

  constructor(private router: Router, private uploadSvc: UploadService,
              private ar: ActivatedRoute) { }

  id!: string
  idSub$!: Subscription

  ngOnInit(): void {
    this.width = Math.floor(window.innerWidth * .9)
    if (this.ar.snapshot.params['id']) {
      this.id = this.ar.snapshot.params['id']
      this.idSub$ = this.ar.params.subscribe(v => {
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.id = v.id
      })
    }
  }

  snapshot(img: WebcamImage) {
    this.uploadSvc.dataUrl = img.imageAsDataUrl
    console.info("upload photo snap: " + this.id)
    if (this.id) {
      this.router.navigate(['/masterKitchen/upload/form', this.id])
    } else {
      this.router.navigate(['/masterKitchen/upload/form'])
    }
  }

  takePicture() {
    this.trigger.next()
  }

}
