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

    @GetMapping("/recipes/{numPage}")
    // @Cacheable(value="#query", key = "#numPage  ")
    public ResponseEntity<?> getRecipes(@RequestParam("query") String query,
            @PathVariable("numPage") int numPage, 
            @RequestParam(required = false) String _contValue) {

        System.out.println(">>> query obtained: " + query);
        System.out.println(">>> numPage obtained: " + numPage);
        System.out.println(">>>> _contValue in controller: " + _contValue);

        Optional<?> getRecipesOtp = edaSvc.getRecipesId(query, numPage, _contValue);

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

        // System.out.println(">>> recipeId: " + id);

        Optional<?> getRecipeDetailOpt = edaSvc.getRecipeDetails(id);
        if (getRecipeDetailOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getRecipeDetailOpt.get() instanceof String == true) {
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            return ResponseEntity.ok(getRecipeDetailOpt.get()); 
        }
    }

    @GetMapping("/recipe/label/{id}")
    public ResponseEntity<String> getRecipeLabel(@PathVariable("id") String id) {
        Optional<?> getRecipeLabelOpt = edaSvc.getRecipeLabelById(id);
        if (getRecipeLabelOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getRecipeLabelOpt.get().toString().contains("error")) {
            // System.out.println(">>>> internal error");
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            // System.out.println(">>>> success!   ");
            String label = getRecipeLabelOpt.get().toString();
            return ResponseEntity.ok(label); 
        }
    } 
}
