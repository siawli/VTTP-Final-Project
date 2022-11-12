package VTTP.FinalProject.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VTTP.FinalProject.models.Recipe;
import VTTP.FinalProject.models.RecipeDetailsResponse;
import VTTP.FinalProject.services.EdamamService;
import VTTP.FinalProject.services.SavedRecipesService;

@RestController
@RequestMapping("/search")
public class EdamamRestController {

    @Autowired
    private EdamamService edaSvc;

    @Autowired
    private SavedRecipesService savedRecipesSvc;

    @GetMapping("/recipes/{numPage}")
    public ResponseEntity<?> getRecipes(@RequestParam("query") String query,
            @PathVariable("numPage") int numPage, 
            @RequestParam(required = false) String _contValue) {

        Optional<?> getRecipesOtp = edaSvc.getRecipesId(query, numPage, _contValue);

        if (getRecipesOtp.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getRecipesOtp.get() instanceof String == true) {
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            return ResponseEntity.ok(getRecipesOtp.get());
        }
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<?> getRecipeDetails(@PathVariable("id") String id,
            @RequestParam String email) {

        Optional<?> getRecipeDetailOpt = edaSvc.getRecipeDetails(id);
        if (getRecipeDetailOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getRecipeDetailOpt.get() instanceof String == true) {
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            RecipeDetailsResponse recipeDetResp = new RecipeDetailsResponse();
            recipeDetResp.setRecipe((Recipe)getRecipeDetailOpt.get());
            Boolean isSaved = savedRecipesSvc.isRecipeSaved(email, id);
            recipeDetResp.setSaved(isSaved);
            return ResponseEntity.ok(recipeDetResp); 
        }
    }

    @GetMapping("/recipe/label/{id}")
    public ResponseEntity<String> getRecipeLabel(@PathVariable("id") String id) {
        Optional<?> getRecipeLabelOpt = edaSvc.getRecipeLabelById(id);
        if (getRecipeLabelOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No results found");
        } else if (getRecipeLabelOpt.get().toString().contains("error")) {
            return ResponseEntity.internalServerError().body("Internal error!");
        } else {
            String label = getRecipeLabelOpt.get().toString();
            return ResponseEntity.ok(label); 
        }
    } 
}
