import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Post } from 'src/app/models';
import { ExploreService } from 'src/app/services/explore.service';

@Component({
  selector: 'app-latest',
  templateUrl: './latest.component.html',
  styleUrls: ['./latest.component.css']
})
export class LatestComponent implements OnInit {

  constructor(private exploreSvc: ExploreService,
    private ar: ActivatedRoute) { }

  // @Input()
  // set _label(label: string) {
  //   this._label = label;
  // }
  // get _label(): string {
  //   return this._label;
  // }

  page!: string
  pageSub$!: Subscription
  id!: string
  idSub$!: Subscription

  allPosts: Post[] = []

  ngOnInit(): void {
    if (this.ar.snapshot.params['page']) {
      this.page = this.ar.snapshot.params['query']
      this.pageSub$ = this.ar.params.subscribe(v => {
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.page = v.page
        if (this.page.includes("latest")) {
          this.getAllPosts()
        } else if (this.page.includes("popular")) {
          this.getPopularPosts()
        } else if (this.page.includes("likedPosts")) {
          this.getLikedPosts();
        } else if (this.page.includes("myPosts")) {
          this.getMyPosts();
        }
      })
    } else if (this.ar.snapshot.params['id']) {
        this.id = this.ar.snapshot.params['id']
        this.idSub$ = this.ar.params.subscribe(b => {
          console.info('>subscribe: ', b)
          // @ts-ignore
          console.info(">>>>> under search")
        })
      }
    }

    getMyPosts() {
      this.exploreSvc.getMyPost()
        .then(result => {
          this.allPosts = result
          this.getImageFromS3(this.allPosts)
        })
        .catch(error => console.info("error in getMyPosts: " + error))
    }

    getLikedPosts() {
      this.exploreSvc.getAllLikedPost()
        .then(result => {
          this.allPosts = result
          this.getImageFromS3(this.allPosts)
        })
        .catch(error => console.info("error in getPopularPosts: " + error))
    }

    getAllPosts() {
      this.exploreSvc.getAllPost()
      .then(result => {
        this.allPosts = result
        this.getImageFromS3(this.allPosts)
      })
      .catch(error => {
        console.info("error in getAllPosts: " + error)
      })
    }

    getPopularPosts() {
      this.exploreSvc.getPopularPost()
      .then(result => {
        this.allPosts = result;
        this.getImageFromS3(this.allPosts)
      })
      .catch(error => console.info("error in getPopularPosts: " + error))

    }

    getImageFromS3(posts: Post[]) {
      for (let post of posts) {
        this.exploreSvc.getImageFromS3(post.imageUUID)
          .then(result => {
            const reader = new FileReader();
            reader.readAsDataURL(result);
            reader.onload = _event => {
              post.imageUUID = reader.result as string
            }
          })
          .catch(error => {
            console.info("error from getting image from AmazonS3: " + error)
          })
      }
    }

    likedPost(post: Post) {
      if (post.liked) {
        post.likes -= 1
        post.liked = false
        this.exploreSvc.updateLikesOnPost(post.post_id, "unlike")
          .then(result => {
            console.info(">>> unliked: " + result)
          })
          .catch(error => {
            console.info(">>> error unliked: " + error)
          })
      } else {
        post.likes += 1
        post.liked = true
        this.exploreSvc.updateLikesOnPost(post.post_id, "add")
          .then(result => {
            console.info(">>> liked: " + result)
          })
          .catch(error => {
            console.info(">>> error liked: " + error)
          })
      }
    }

  }
