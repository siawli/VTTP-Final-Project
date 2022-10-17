package VTTP.FinalProject.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import VTTP.FinalProject.models.Post;
import VTTP.FinalProject.repositories.ExploreRespository;

@Service
public class ExploreService {

    @Autowired
    private ExploreRespository exploreRepo;

    public Optional<List<Post>> getAllPosts() {

        Optional<SqlRowSet> resultOpt = exploreRepo.getAllPostsDateDesc();

        if (resultOpt.isEmpty()) {
            return Optional.empty();
        }
        SqlRowSet result = resultOpt.get();
        List<Post> allPosts = new LinkedList<>();
        while (result.next()) {
            Post post = Post.createPost(result);
            allPosts.add(post);
        }

        return Optional.of(allPosts);

    }
    
}
