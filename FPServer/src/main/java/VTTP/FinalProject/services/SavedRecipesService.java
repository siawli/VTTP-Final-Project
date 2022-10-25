package VTTP.FinalProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTP.FinalProject.repositories.SavedRecipesRepository;

@Service
public class SavedRecipesService {

    @Autowired
    private SavedRecipesRepository recipeRepo;

    public boolean updateSavedRecipe(String email, String alteration, String recipe_id) {
        return recipeRepo.alterSavedRecipe(recipe_id, email, alteration);
    }

    public boolean isRecipeSaved(String email, String recipe_id) {
        return recipeRepo.isRecipeSaved(recipe_id, email);
    }
    
}
