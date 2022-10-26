package VTTP.FinalProject.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class SavedRecipe {
    private String recipe_label;
    private String recipe_id;

    public String getRecipe_label() {
        return recipe_label;
    }
    public void setRecipe_label(String recipe_label) {
        this.recipe_label = recipe_label;
    }
    public String getRecipe_id() {
        return recipe_id;
    }
    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public static SavedRecipe createSavedRecipes(SqlRowSet result) {
        SavedRecipe savedRecipe = new SavedRecipe();
        savedRecipe.setRecipe_id(result.getString("recipe_id"));
        savedRecipe.setRecipe_label(result.getString("recipe_label"));

        return savedRecipe;
    }
}
