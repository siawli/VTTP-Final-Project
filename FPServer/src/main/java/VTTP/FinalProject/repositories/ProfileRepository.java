package VTTP.FinalProject.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static VTTP.FinalProject.repositories.Queries.*;

@Repository
public class ProfileRepository {

    @Autowired
    private JdbcTemplate template;

    public Optional<SqlRowSet> getAllLikedPosts(String email) {
        SqlRowSet result = template.queryForRowSet(SQL_GET_ALL_LIKED_POSTS, email);

        if (!result.next()) {
            return Optional.empty();
        }
        result.previous();
        return Optional.of(result);
    }
    
}
