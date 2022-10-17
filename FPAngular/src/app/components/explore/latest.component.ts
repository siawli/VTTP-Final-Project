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
      this.exploreSvc.getImageFromS3(post.imageUUID)
        .then(result => {
          console.info("result from getImageAmazonS3: " + result)
          const reader = new FileReader();
          reader.readAsDataURL(result);
          reader.onload = _event => {
          post.imageUUID = reader.result as string
          post.isLiked = false
          }
        })
        .catch(error => {
          console.info("error from getting image from AmazonS3: " + error)
        })
    }
  }

  likedPost(post: Post) {
    if (post.isLiked) {
      post.likes -= 1
      post.isLiked = false
    } else {
      post.likes += 1
      post.isLiked = true
    }
  }

}
