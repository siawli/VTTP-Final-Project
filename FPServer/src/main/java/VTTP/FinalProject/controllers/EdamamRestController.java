package VTTP.FinalProject.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VTTP.FinalProject.models.RecipeResponse;
import VTTP.FinalProject.services.EdamamService;

@RestController
@RequestMapping("/search")
public class EdamamRestController {

    @Autowired
    private EdamamService edaSvc;

    // @Cacheable(value="#query", key = "#numPage", unless = "#result == null")
    @GetMapping("/recipes/{numPage}")
    public ResponseEntity<?> getRecipes(@RequestParam("query") String query,
            @PathVariable("numPage") int numPage) {

        System.out.println(">>> query obtained: " + query);
        System.out.println(">>> numPage obtained: " + numPage);

        Optional<?> getRecipesOtp = edaSvc.getRecipesId(query, numPage);

        if (getRecipesOtp.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getRecipesOtp.get() instanceof String == true) {
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            RecipeResponse response = (RecipeResponse)getRecipesOtp.get();
            // System.out.println(">>>> recipes length: " + response.getRecipes().size());
            // System.out.println(">>>> next URL: " + response.getNextURL());
            return ResponseEntity.ok(getRecipesOtp.get());
        }
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<?> getRecipeDetails(@PathVariable("id") String id) {

        System.out.println(">>> recipeId: " + id);

        Optional<?> getRecipeDetailOpt = edaSvc.getRecipeDetails(id);
        if (getRecipeDetailOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getRecipeDetailOpt.get() instanceof String == true) {
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            return ResponseEntity.ok(getRecipeDetailOpt.get()); 
        }
    }

    
}
