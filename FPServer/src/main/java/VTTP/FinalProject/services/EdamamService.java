package VTTP.FinalProject.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.StackWalker.Option;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import VTTP.FinalProject.models.Recipe;
import VTTP.FinalProject.models.RecipeResponse;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class EdamamService implements Serializable {

    @Value("${eda.app.id}")
    private String app_id;

    @Value("${eda.app.key}")
    private String app_key;

    @Autowired
    private CachingService cacheSvc;

    // @Autowired
    // private RecipesCacheRepository recipesCacheRepo;

    private final String DEFAULT_URL = "https://api.edamam.com/api/recipes/v2";

    public Optional<?> getRecipesId(String query, int pageNum, String contValue) {
        
        String url = null;
        System.out.println(">>> contValue in EdaSvc: " + contValue);
        if (contValue == null) {
            url = buildUrl(DEFAULT_URL)
                .queryParam("q", query)
                .toUriString();
        } else {
                url = buildUrl(DEFAULT_URL)
                .queryParam("q", query)
                .toUriString();
                url += "&_cont=" + contValue;
                System.out.println(">>> url: " + url);
        }

        RecipeResponse recipeResponse = new RecipeResponse();
        JsonObject data = null;

        try {
            data = getDataFromAPI(url);
        } catch (Exception ex) {
            return Optional.of("API error!");
        }

        if (data.getInt("to") == 0) {
            return Optional.empty();
        }

        // get next recipes link
        String NEXT_URL = data.getJsonObject("_links")
                .getJsonObject("next")
                .getString("href");
        String _contValue = NEXT_URL.split("&")[2].substring(6);
        // System.out.println(">>> _cont: " + _contValue);
        recipeResponse.setNextURL(_contValue);

        // get recipe_id
        List<Recipe> recipes = new LinkedList<>();
        recipes = cacheSvc.getListOfRecipesId(data, query, pageNum);
        recipeResponse.setRecipes(recipes);

        // recipesCacheRepo.storeQueryByPage(query, pageNum, recipeResponse);

        return Optional.of(recipeResponse);
    }

    public Optional<?> getRecipeDetails(String id) {
        String url = buildUrl(DEFAULT_URL + "/" + id)
                .queryParam("id", id)
                .toUriString();

        JsonObject data = null;

        try {
            data = getDataFromAPI(url);
        } catch (Exception ex) {
            return Optional.of("API error!");
        }

        try {
            JsonObject recipe = data.getJsonObject("recipe");
            Recipe recipeModel = getSimilarData(recipe);
            // get source link
            recipeModel.setLink(recipe.getString("url"));

            // get servings
            int yield = recipe.getInt("yield");
            recipeModel.setServings(yield);

            // get ingredientLines
            JsonArray ingredientLines = recipe.getJsonArray("ingredientLines");
            for (JsonValue ingredient: ingredientLines) {
                String ingred = ingredient.toString().substring(1, ingredient.toString().length()-1);
                recipeModel.addIngredientLines(ingred);
            }

            // get calories
            Float totalCal = Float.valueOf(recipe.get("calories").toString());
            recipeModel.setCalories((int)Math.ceil(totalCal/yield));

            return Optional.of(recipeModel);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public Optional<String> getRecipeLabelById(String id) {
        System.out.println(">>>>> in get RecipeLabelById");
        String url = buildUrl(DEFAULT_URL + "/" + id)
                .queryParam("id", id)
                .toUriString();

        JsonObject data = null;

        try {
            data = getDataFromAPI(url);
        } catch (Exception ex) {
            System.out.println(">>> API error");
            return Optional.of("API error!");
        }

        String recipe_label = "";

        try {
            System.out.println(">>>>>>>> here");
            JsonObject recipe = data.getJsonObject("recipe");
            recipe_label = recipe.getString("label");
            System.out.println(">>>> recipe_label: " + recipe_label);
            return Optional.of(recipe_label);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private UriComponentsBuilder buildUrl(String url) {
        UriComponentsBuilder urlB = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("app_id", app_id)
                .queryParam("app_key", app_key)
                .queryParam("type", "public");

        return urlB;
    }

    public static Recipe getSimilarData(JsonObject recipe) {
        Recipe recipeModel = new Recipe();

        // get recipe_id
        String uri = recipe.getString("uri");
        int idIndex = uri.indexOf("#");
        String recipe_id = uri.substring(idIndex + 8);
        recipeModel.setRecipe_id(recipe_id);

        // set UUID for easier reference
        recipeModel.setStoredUUID(UUID.randomUUID().toString().substring(0, 8));

        // get label
        recipeModel.setLabel(recipe.getString("label"));

        // get image regular
        String imageLink = recipe.getJsonObject("images")
                .getJsonObject("REGULAR")
                .getString("url");
        recipeModel.setImage(imageLink);

        return recipeModel;
    }

    private JsonObject getDataFromAPI(String url) throws IOException {
        InputStream is = null;
        try {
            is = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }

        JsonReader reader = Json.createReader(is);
        JsonObject data = reader.readObject();

        return data;
    }
}
