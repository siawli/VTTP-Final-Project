package VTTP.FinalProject.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTP.FinalProject.models.FoodieUser;
import VTTP.FinalProject.repositories.UserRepository;

@Service
public class UserService {

    private UserRepository userRepo;
    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<FoodieUser> getUser(String email) {
        return userRepo.checkUserExistsByEmail(email);
    }

    public Optional<String> createUser(FoodieUser user) {

        Optional<FoodieUser> foodieUserOpt = getUser(user.getEmail());
        if (foodieUserOpt.isPresent()) {
            return Optional.empty();
        }

        if (userRepo.createNewUser(user)) {
            return Optional.of("User created");
        } else {
            System.out.println(">>>> in failed");
            return Optional.of("Internal error! User not created. Please try again.");
        }
    }

}
