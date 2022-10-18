package VTTP.FinalProject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import VTTP.FinalProject.models.Post;

import static VTTP.FinalProject.repositories.Queries.*;

@Repository
public class PostRepository {

    @Autowired
    private JdbcTemplate template;

    public boolean uploadPost(Post post) {
        int added = template.update(SQL_NEW_POST,
            post.getEmail(), post.getUsername(),
            post.getTitle(), post.getCaption(), post.getRecipe_id(),
            post.getRecipe_label(), post.getRatings(), 0,
            post.getDate(), post.getImageUUID());
            // email, title, caption, recipe_id, ratings, likes, date, imageUUID
        
        return added == 1;
    
    }
    
}
