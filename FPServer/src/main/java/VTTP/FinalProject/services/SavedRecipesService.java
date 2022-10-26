package VTTP.FinalProject.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import VTTP.FinalProject.models.SavedRecipe;
import VTTP.FinalProject.repositories.SavedRecipesRepository;

@Service
public class SavedRecipesService {

    @Autowired
    private SavedRecipesRepository recipeRepo;

    public boolean updateSavedRecipe(String email, String recipe_label, 
            String alteration, String recipe_id) {
        return recipeRepo.alterSavedRecipe(recipe_id, recipe_label, email, alteration);
    }

    public boolean isRecipeSaved(String email, String recipe_id) {
        return recipeRepo.isRecipeSaved(recipe_id, email);
    }

    public Optional<List<SavedRecipe>> getSavedRecipes(String email) {
        SqlRowSet result = recipeRepo.getSavedRecipes(email);
        if (!result.next()) {
            return Optional.empty();
        }
        result.previous();
        List<SavedRecipe> savedRecipes = new LinkedList<>();

        while(result.next()) {
            savedRecipes.add(SavedRecipe.createSavedRecipes(result));
        }

        return Optional.of(savedRecipes);
    }
    
}
