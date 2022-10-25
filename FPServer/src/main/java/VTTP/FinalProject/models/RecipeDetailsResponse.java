package VTTP.FinalProject.models;

public class RecipeDetailsResponse {
    private Recipe recipe;
    private boolean isSaved;

    public Recipe getRecipe() {
        return recipe;
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    public boolean isSaved() {
        return isSaved;
    }
    public void setSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }
}
