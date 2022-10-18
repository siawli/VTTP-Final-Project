package VTTP.FinalProject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static VTTP.FinalProject.repositories.Queries.*;

import java.util.Optional;

@Repository
public class ExploreRespository {

    @Autowired
    private JdbcTemplate template;

    public Optional<SqlRowSet> getAllPostsDateAsc(String email) {
        SqlRowSet result = template.queryForRowSet(SQL_GET_ALL_POSTS, email);
        if (!result.next()) {
            return Optional.empty();
        }
        result.previous();
        return Optional.of(result);
    }

    public boolean updateLikesOnPost(int post_id, String alteration) {
        SqlRowSet result = template.queryForRowSet(SQL_GET_LIKES_BY_POST, post_id);
        result.next();
        int likes = result.getInt("likes");
        if (alteration.contains("add")) {
            likes++;
        } else {
            likes--;
        }
        int added = template.update(SQL_ALTER_LIKES_BY_POST, likes, post_id);

        return added == 1;
    }

    public boolean alterPostInLikedPost(int post_id, String email, String alteration) {
        int added = 0;
        if (alteration.contains("add")) {
            added = template.update(SQL_ADD_LIKED_POST, email, post_id);
        } else {
            added = template.update(SQL_DELETE_LIKED_POST, post_id, email);
        }
        return added == 1;
    }

    public void getPopularPost() {

    }
    
}
