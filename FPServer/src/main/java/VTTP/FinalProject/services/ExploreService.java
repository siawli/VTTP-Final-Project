package VTTP.FinalProject.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import VTTP.FinalProject.models.Post;
import VTTP.FinalProject.repositories.ExploreRespository;

@Service
public class ExploreService {

    @Autowired
    private ExploreRespository exploreRepo;

    public Optional<List<Post>> getAllPosts(String email) {

        Optional<SqlRowSet> resultOpt = exploreRepo.getAllPostsDateAsc(email);

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

    @Transactional(rollbackFor = Exception.class)
    public void alterLikes(String alteration, int post_id, String email) throws Exception {
        if (!exploreRepo.updateLikesOnPost(post_id, alteration)) {
            throw new Exception("Failed to update likes");
        }
        if (!exploreRepo.alterPostInLikedPost(post_id, email, alteration)) {
            throw new Exception("Failed to remove/add post from likedPosts");
        }
    }
    
}
