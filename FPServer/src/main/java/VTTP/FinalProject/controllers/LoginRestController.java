package VTTP.FinalProject.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import VTTP.FinalProject.exceptions.UserNotCreatedException;
import VTTP.FinalProject.models.FoodieUser;
import VTTP.FinalProject.services.EmailService;
import VTTP.FinalProject.services.ProfileService;

@RestController
public class LoginRestController {

    @Autowired
    private ProfileService userSvc;

    @Autowired
    private EmailService emailSvc;

    @GetMapping("/explore")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok(null);
    }

    @PostMapping(path="/signUp", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody FoodieUser user) 
            throws UserNotCreatedException {
        
        Optional<String> userCreatedMsgOpt;
        try {
            userCreatedMsgOpt = userSvc.createUser(user);
        } catch (Exception e) {
            throw new UserNotCreatedException();
        }

        if (userCreatedMsgOpt.isEmpty()) {
            throw new UserNotCreatedException("User already exists! Please login instead.");
        }

        String userCreatedMsg = userCreatedMsgOpt.get();

        if (userCreatedMsg.contains("created")) {
            emailSvc.sendEmailUponSignUp(user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("\"User Created!\"");
        } else {
            throw new UserNotCreatedException(userCreatedMsg);
        }        
    }

    @ExceptionHandler(UserNotCreatedException.class)
    ResponseEntity<String> createUserError(final UserNotCreatedException e) {
        if (e.getMessage().contains("exists")) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
