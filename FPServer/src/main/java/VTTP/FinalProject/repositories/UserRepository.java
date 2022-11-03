package VTTP.FinalProject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import VTTP.FinalProject.models.FoodieUser;
import static VTTP.FinalProject.repositories.Queries.*;

import java.util.Optional;

@Repository
public class UserRepository {

    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserRepository(@Lazy PasswordEncoder encoder) {
        this.passwordEncoder = encoder;
    }

    @Autowired
    private JdbcTemplate template;

    public Optional<FoodieUser> checkUserExistsByEmail(String email) {
        SqlRowSet result = template.queryForRowSet(SQL_GET_USER, email);

        if (!result.next()) {
            return Optional.empty();
        } else {
            FoodieUser user = new FoodieUser();
            user.setUsername(result.getString("username"));
            user.setEmail(email);
            user.setPassword(result.getString("password"));
            return Optional.of(user);
        }
    }

    public boolean createNewUser(FoodieUser user) {
        int added = template.update(SQL_NEW_USER,
                user.getUsername(), user.getEmail(), 
                passwordEncoder.encode(user.getPassword()));
        
        return added == 1;
    }
    
}
