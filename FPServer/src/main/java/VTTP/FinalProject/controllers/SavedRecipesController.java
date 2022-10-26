package VTTP.FinalProject.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VTTP.FinalProject.models.SavedRecipe;
import VTTP.FinalProject.services.SavedRecipesService;

@RestController
@RequestMapping("/saved")
public class SavedRecipesController {

    @Autowired
    private SavedRecipesService savedRecipesSvc;

    @GetMapping("/allRecipes")
    public ResponseEntity<?> getAllSavedRecipes(@RequestParam String email) {
        Optional<List<SavedRecipe>> savedRecipesOpt = savedRecipesSvc.getSavedRecipes(email);
        if (savedRecipesOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No recipes saved");
        }

        return ResponseEntity.ok(savedRecipesOpt.get());
    }

    @GetMapping("/alterSaved")
    public ResponseEntity<?> alterSavedRecipe(
            @RequestParam String email, @RequestParam String recipe_label,
            @RequestParam String recipe_id, @RequestParam String alteration) {

        boolean added = savedRecipesSvc.updateSavedRecipe(email, recipe_label, alteration, recipe_id);
        if (added) {
            return ResponseEntity.ok("\"Successful saved recipes\"");
        } else {
            return ResponseEntity.internalServerError().body("\"Error! Unable to save recipe!\"");
        }
    }
}
