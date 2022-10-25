package VTTP.FinalProject.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

public class Recipe implements Serializable {

    private String recipe_id;
    private String storedUUID;
    private String label;
    private String image;
    private String link;
    private int servings;
    private List<String> ingredientLines = new LinkedList<>();
    private int calories;

    public String getRecipe_id() {
        return recipe_id;
    }
    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }
    public String getStoredUUID() {
        return storedUUID;
    }
    public void setStoredUUID(String storedUUID) {
        this.storedUUID = storedUUID;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public int getServings() {
        return servings;
    }
    public void setServings(int servings) {
        this.servings = servings;
    }
    public List<String> getIngredientLines() {
        return ingredientLines;
    }
    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }
    public void addIngredientLines(String ingredientLine) {
        this.ingredientLines.add(ingredientLine);
    }
    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    
}
