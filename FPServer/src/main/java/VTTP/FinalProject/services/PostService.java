package VTTP.FinalProject.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import VTTP.FinalProject.models.Post;
import VTTP.FinalProject.repositories.PostRespository;

@Service
public class PostService {

    @Autowired
    private PostRespository postRepo;

    @Transactional(rollbackFor = Exception.class)
    public void deletePost(String email, int post_id) throws Exception {
        if (!postRepo.deletePost(post_id, email)) {
            throw new Exception("Failed to delete post");
        }
        try {
            postRepo.deleteLikedPostsById(post_id);
        } catch (Exception ex) {
            throw new Exception("Failed to delete from likedPosts");
        }
    }

    public boolean uploadPostDatabase(Post post) {
        return postRepo.uploadPost(post);
    }

    public Optional<List<Post>> getAllPosts(String email) {
        Optional<SqlRowSet> resultOpt = postRepo.getAllPostsDateAsc(email);
        return createListPost(resultOpt);
    }

    public Optional<List<Post>> getAllLikedPosts(String email) {
        Optional<SqlRowSet> resultOpt = postRepo.getAllLikedPosts(email);
        return createListPost(resultOpt);
    }

    public Optional<List<Post>> getPopularPosts(String email) {
        Optional<SqlRowSet> resultOpt = postRepo.getPopularPost(email);
        return createListPost(resultOpt);
    }

    public Optional<List<Post>> getMyPosts(String email) {
        Optional<SqlRowSet> resultOpt = postRepo.getMyPosts(email);
        return createListPost(resultOpt);
    }

    public Optional<List<Post>> getPostsByRecipeId(String email, String recipe_id) {
        Optional<SqlRowSet> resultOpt = postRepo.getPostByRecipeId(email, recipe_id);
        return createListPost(resultOpt);
    }

    @Transactional(rollbackFor = Exception.class)
    public void alterLikes(String alteration, int post_id, String email) throws Exception {
        if (!postRepo.alterPostInLikedPost(post_id, email, alteration)) {
            throw new Exception("Failed to remove/add post from likedPosts");
        }
        if (!postRepo.updateLikesOnPost(post_id, alteration)) {
            throw new Exception("Failed to update likes");
        }
    }

    private Optional<List<Post>> createListPost(Optional<SqlRowSet> resultOpt) {
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
