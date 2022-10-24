package VTTP.FinalProject.models;

import java.io.Serializable;
import java.util.List;

public class RecipeResponse implements Serializable {
    private List<Recipe> recipes;
    private String nextURL;

    public List<Recipe> getRecipes() {
        return recipes;
    }
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    public String getNextURL() {
        return nextURL;
    }
    public void setNextURL(String nextURL) {
        this.nextURL = nextURL;
    }    
}
