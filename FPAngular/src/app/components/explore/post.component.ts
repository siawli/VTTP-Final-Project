import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Post } from 'src/app/models';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  constructor(private postSvc: PostService,
    private ar: ActivatedRoute) { }

  page!: string
  pageSub$!: Subscription
  id!: string
  idSub$!: Subscription

  allPosts: Post[] = []
  noPosts = false;

  ngOnInit(): void {
    if (this.ar.snapshot.params['page']) {
      this.page = this.ar.snapshot.params['query']
      this.pageSub$ = this.ar.params.subscribe(v => {
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.page = v.page
        if (this.page.includes("latest")) {
          this.allPosts = []
          this.getAllPosts()
        } else if (this.page.includes("popular")) {
          this.allPosts = []
          this.getPopularPosts()
        } else if (this.page.includes("likedPosts")) {
          this.allPosts = []
          this.getLikedPosts();
        } else if (this.page.includes("myPosts")) {
          this.allPosts = []
          this.getMyPosts();
        }
      })
    } else if (this.ar.snapshot.params['id']) {
        this.id = this.ar.snapshot.params['id']
        this.idSub$ = this.ar.params.subscribe(b => {
          console.info('>subscribe: ', b)
          // @ts-ignore
          console.info(">>>>> under search")
          this.getPostsByRecipeId(b['id'])
        })
      }
    }

    getPostsByRecipeId(recipe_id: string) {
      this.postSvc.getPostsByRecipeId(recipe_id)
        .then(result => {
          console.info(">>>>> result from getPostsByRecipeId: " + result.length)
          this.getImageFromS3(this.allPosts)
          setTimeout(() => {
            this.allPosts = result
          }, 800);
        })
        .catch(error => {
          this.noPosts = true;
          console.info("error in getPostsyRecipeId: " + error)
      })
    }

    getMyPosts() {
      this.postSvc.getMyPost()
        .then(result => {
          this.getImageFromS3(result)
          setTimeout(() => {
            this.allPosts = result
          }, 800);
        })
        .catch(error => {
          this.noPosts = true;
          console.info("error in getMyPosts: " + error)
        })
    }

    getLikedPosts() {
      this.postSvc.getAllLikedPost()
        .then(result => {
          this.getImageFromS3(result)
          setTimeout(() => {
            this.allPosts = result
          }, 800);
        })
        .catch(error => {
          this.noPosts = true;
          console.info("error in getPopularPosts: " + error)
        })
    }

    getAllPosts() {
      this.postSvc.getAllPost()
      .then(result => {
        this.getImageFromS3(result)
        setTimeout(() => {
          this.allPosts = result
        }, 800);
      })
      .catch(error => {
        this.noPosts = true;
        console.info("error in getAllPosts: " + error)
      })
    }

    getPopularPosts() {
      this.postSvc.getPopularPost()
      .then(result => {
        this.getImageFromS3(result)
        setTimeout(() => {
          this.allPosts = result
        }, 800);
      })
      .catch(error => {
        this.noPosts = true;
        console.info("error in getPopularPosts: " + error)
      })

    }

    getImageFromS3(posts: Post[]) {
      for (let post of posts) {
        this.postSvc.getImageFromS3(post.imageUUID)
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
        console.info(">>> post likes unlike: " + post.likes)
        this.postSvc.updateLikesOnPost(post.post_id, "unlike")
        .then(result => {
            post.liked = false
            post.likes -= 1
            console.info(">>> unliked: " + result)
          })
          // .catch(error => {
          //   console.info(">>> error unliked: " + error)
          // })
      } else {
        console.info(">>> post likes like: " + post.likes)
        this.postSvc.updateLikesOnPost(post.post_id, "add")
        .then(result => {
            post.liked = true
            post.likes += 1
            console.info(">>> liked: " + result)
          })
          // .catch(error => {
          //   console.info(">>> error liked: " + error)
          // })
      }
    }

  }
