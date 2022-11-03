package VTTP.FinalProject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static VTTP.FinalProject.repositories.Queries.*;

@Repository
public class SavedRecipesRepository {
    
    @Autowired
    private JdbcTemplate template;

    public boolean alterSavedRecipe(String recipe_id, String recipe_label,
             String email, String alteration) {
        int added = 0;
        if (alteration.contains("add")) {
            added = template.update(SQL_ADD_SAVED_RECIPE, email, recipe_id, recipe_label);
        } else {
            added = template.update(SQL_DELETE_SAVED_RECIPE, recipe_id, email);
        }

        return added == 1;
    }

    public boolean isRecipeSaved(String recipe_id, String email) {
        SqlRowSet result = template
            .queryForRowSet(SQL_IS_RECIPE_SAVED, email, recipe_id);

        if (!result.next()) {
            return false;
        } else {
            return true;
        }
    }

    public SqlRowSet getSavedRecipes(String email) {
        return template.queryForRowSet(SQL_GET_SAVED_RECIPES, email);
    }
}
