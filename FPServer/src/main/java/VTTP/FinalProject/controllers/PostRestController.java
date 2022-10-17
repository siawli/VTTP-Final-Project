package VTTP.FinalProject.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import VTTP.FinalProject.models.Post;
import VTTP.FinalProject.services.PostService;

@RestController()
@RequestMapping("/upload")
public class PostRestController {

    @Autowired
    private PostService postSvc;

    @PostMapping("post")
    public ResponseEntity<String> uploadPost(@RequestBody Post post) {

        System.out.println(">>>> in uploadPost controller");
        System.out.println(">>>> post: " + post.toString());
        if (postSvc.uploadPostDatabase(post)) {
            System.out.println(">>> successful upload of post!");
            return ResponseEntity.ok("\"Successful upload of post!\"");
        }

        return ResponseEntity.internalServerError().body("\"Failed to upload post!\"");

    }
}
