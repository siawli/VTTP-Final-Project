package VTTP.FinalProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTP.FinalProject.models.Post;
import VTTP.FinalProject.repositories.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;

    public boolean uploadPostDatabase(Post post) {
        return postRepo.uploadPost(post);
    }
}
