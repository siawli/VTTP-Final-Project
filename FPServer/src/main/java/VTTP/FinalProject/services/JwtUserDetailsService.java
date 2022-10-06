package VTTP.FinalProject.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import VTTP.FinalProject.models.FoodieUser;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserService userSvc;
    
    @Autowired
    public void setUserSvc(UserService userSvc) {
        this.userSvc = userSvc;
    }
    public UserService getUserSvc() {
        return this.userSvc;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<FoodieUser> userOpt = userSvc.getUser(email);

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist!");
        }

        FoodieUser foodieUser = userOpt.get();

        return new User(foodieUser.getEmail(), foodieUser.getPassword(), new ArrayList<>());

    }
    
}
