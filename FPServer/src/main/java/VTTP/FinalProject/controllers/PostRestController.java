package VTTP.FinalProject.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VTTP.FinalProject.models.Post;
import VTTP.FinalProject.services.PostService;

@RestController()
@RequestMapping("/post")
public class PostRestController {

    @Autowired
    private PostService postSvc;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPost(@RequestBody Post post) {

        if (postSvc.uploadPostDatabase(post)) {
            return ResponseEntity.ok("\"Successful upload of post!\"");
        }

        return ResponseEntity.internalServerError().body("\"Failed to upload post!\"");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam String email) {

        System.out.println(">>>>> email from Controller: getAllPosts: " + email);
        Optional<List<Post>> postsOpt = postSvc.getAllPosts(email);

        if (postsOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Post> posts = postsOpt.get();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/updateLikes")
    public ResponseEntity<?> updateLikesOnPost(
            @RequestParam int post_id, @RequestParam String alteration,
            @RequestParam String email) {
        
        System.out.println(">>>> post_id & alteration: " + post_id + " " + alteration);

        try {
            postSvc.alterLikes(alteration, post_id, email);
            return ResponseEntity.ok("\"Likes updated successfully\"");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("\"Failed to update likes on post\"");
        }
    }

    @GetMapping("/allLikedPosts")
    public ResponseEntity<?> getAllLikedPosts(@RequestParam String email) {
        Optional<List<Post>> likedPostOpt = postSvc.getAllLikedPosts(email);

        if (likedPostOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(likedPostOpt.get());
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularPosts(@RequestParam String email) {
        Optional<List<Post>> popularPostsOpt = postSvc.getPopularPosts(email);

        if (popularPostsOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(popularPostsOpt.get());
    }

    @GetMapping("/myPosts")
    public ResponseEntity<?> getMyPosts(@RequestParam String email) {
        Optional<List<Post>> myPostsOpt = postSvc.getMyPosts(email);
        
        if (myPostsOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(myPostsOpt.get());
    }

    @GetMapping("/byRecipeId")
    public ResponseEntity<?> getPostsByRecipeId(@RequestParam String email,
            @RequestParam String recipe_id) {

        Optional<List<Post>> postsByRecipeIdOpt = 
            postSvc.getPostsByRecipeId(email, recipe_id);
        
        if (postsByRecipeIdOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(postsByRecipeIdOpt.get());
    }
}
