package VTTP.FinalProject.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VTTP.FinalProject.models.Post;
import VTTP.FinalProject.services.ExploreService;

@RestController()
@RequestMapping("/explore")
public class ExploreRestController {

    @Autowired
    private ExploreService exploreSvc;

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {

        Optional<List<Post>> postsOpt = exploreSvc.getAllPosts();

        if (postsOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Post> posts = postsOpt.get();
        // System.out.println(">>>> posts size: " + posts.size());
        
        return ResponseEntity.ok(posts);
    }
}
