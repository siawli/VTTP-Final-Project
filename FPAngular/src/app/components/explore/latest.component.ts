import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Post } from 'src/app/models';
import { ExploreService } from 'src/app/services/explore.service';

@Component({
  selector: 'app-latest',
  templateUrl: './latest.component.html',
  styleUrls: ['./latest.component.css']
})
export class LatestComponent implements OnInit {

  constructor(private exploreSvc: ExploreService) { }

  allPosts: Post[] = []

  ngOnInit(): void {
    this.exploreSvc.getAllPost()
      .then(result => {
        this.allPosts = result
        console.info("allPosts size: " + this.allPosts.length)
        this.getImageFromS3(this.allPosts)
      })
      .catch(error => {
        console.info("error in getAllPosts: " + error)
      })
  }

  getImageFromS3(posts: Post[]) {
    for (let post of posts) {
      console.info(">>> post is liked?: " + post.post_id + " " + post.liked)
      this.exploreSvc.getImageFromS3(post.imageUUID)
        .then(result => {
          console.info("result from getImageAmazonS3: " + result)
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
