package VTTP.FinalProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import VTTP.FinalProject.services.SavedRecipesService;

@RestController
@RequestMapping("/saved")
public class SavedRecipesController {

    @Autowired
    private SavedRecipesService savedRecipesSvc;

    @GetMapping("/allRecipes")
    public ResponseEntity<?> getAllSavedRecipes(@RequestParam String email) {

        return null;
    }

    @GetMapping("/alterSaved")
    public ResponseEntity<?> alterSavedRecipe(@RequestParam String email, 
            @RequestParam String recipe_id, @RequestParam String alteration) {

        boolean added = savedRecipesSvc.updateSavedRecipe(email, alteration, recipe_id);
        if (added) {
            return ResponseEntity.ok("\"Successful saved recipes\"");
        } else {
            return ResponseEntity.internalServerError().body("\"Error! Unable to save recipe!\"");
        }
    }
}
