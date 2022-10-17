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

    public Optional<SqlRowSet> getAllPostsDateDesc() {
        SqlRowSet result = template.queryForRowSet(SQL_GET_ALL_POSTS);
        if (!result.next()) {
            return Optional.empty();
        }
        result.previous();
        return Optional.of(result);
    }

    public void getPopularPost() {

    }
    
}
