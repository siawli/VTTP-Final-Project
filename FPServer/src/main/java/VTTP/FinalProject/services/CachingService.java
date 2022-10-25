package VTTP.FinalProject.services;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Service;

import VTTP.FinalProject.models.Recipe;
import VTTP.FinalProject.models.RecipeListResponse;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

@Service
public class CachingService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // @RedisHash(timeToLive = 60*15)
    /*
     * @Cacheable(value="query", key = "#query.toString() + #pageNum.toString()")
     * public List<Recipe> getListOfRecipesId(JsonObject data, String query, int
     * pageNum) {
     * 
     * List<Recipe> recipes = new LinkedList<>();
     * 
     * JsonArray hits = data.getJsonArray("hits");
     * for (JsonValue jsonObj : hits) {
     * JsonObject recipe = jsonObj.asJsonObject().getJsonObject("recipe");
     * 
     * recipes.add(EdamamService.getSimilarData(recipe));
     * }
     * System.out.println(">>>> key: " + "query::%s%d".formatted(query, pageNum));
     * System.out.println(">>> value?: " + redisTemplate.keys("query"));
     * redisTemplate.expire("query::%s%d".formatted(query, pageNum), 60*15,
     * TimeUnit.SECONDS);
     * 
     * return recipes;
     * 
     * }
     */

    @Cacheable(value = "query", key = "#query.toString() + #pageNum.toString()")
    public RecipeListResponse getListOfRecipesId(String url, String query, int pageNum) throws Exception {
        RecipeListResponse recipeResponse = new RecipeListResponse();
        JsonObject data = null;

        try {
            data = EdamamService.getDataFromAPI(url);
        } catch (Exception ex) {
            throw new Exception("API error");
        }

        if (data.getInt("to") == 0) {
            throw new Exception("No recipes found");
        }

        System.out.println(">>> calling API:");
        String NEXT_URL = data.getJsonObject("_links")
                .getJsonObject("next")
                .getString("href");
        String _contValue = NEXT_URL.split("&")[2].substring(6);
        // System.out.println(">>> _cont: " + _contValue);
        recipeResponse.setNextURL(_contValue);

        List<Recipe> recipes = new LinkedList<>();

        JsonArray hits = data.getJsonArray("hits");
        for (JsonValue jsonObj : hits) {
            JsonObject recipe = jsonObj.asJsonObject().getJsonObject("recipe");

            recipes.add(EdamamService.getSimilarData(recipe));
        }

        recipeResponse.setRecipes(recipes);
        return recipeResponse;
    }

    @Cacheable(value = "recipeDetails", key = "#recipe_id.substring(0, 8)")
    public Recipe getRecipeDetails(String url, String recipe_id) throws Exception {
        JsonObject data = null;

        try {
            data = EdamamService.getDataFromAPI(url);
        } catch (Exception ex) {
            throw new Exception("No recipe!");
        }

        try {
            JsonObject recipe = data.getJsonObject("recipe");
            Recipe recipeModel = EdamamService.getSimilarData(recipe);
            // get source link
            recipeModel.setLink(recipe.getString("url"));

            // get servings
            int yield = recipe.getInt("yield");
            recipeModel.setServings(yield);

            // get ingredientLines
            JsonArray ingredientLines = recipe.getJsonArray("ingredientLines");
            for (JsonValue ingredient : ingredientLines) {
                String ingred = ingredient.toString().substring(1, ingredient.toString().length() - 1);
                recipeModel.addIngredientLines(ingred);
            }

            // get calories
            Float totalCal = Float.valueOf(recipe.get("calories").toString());
            recipeModel.setCalories((int) Math.ceil(totalCal / yield));

            return recipeModel;
        } catch (Exception ex) {
            throw new Exception("API error");
        }
    }
}
