package VTTP.FinalProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import VTTP.FinalProject.configurations.JwtUtil;
import VTTP.FinalProject.models.JwtRequest;
import VTTP.FinalProject.models.JwtResponse;
import VTTP.FinalProject.services.JwtUserDetailsService;
import VTTP.FinalProject.services.ProfileService;

@RestController
public class JwtAuthenticateRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsSvc;

    @Autowired
    private ProfileService userSvc;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthentication(@RequestBody JwtRequest jwtRequest)
            throws Exception {

        String email = jwtRequest.getEmail();
        String password = jwtRequest.getPassword();

        try {
            System.out.println(">>>>> in /authenticate");
            authenticate(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        final UserDetails userDetails = jwtUserDetailsSvc
                .loadUserByUsername(email);

        final String token = jwtUtil.generateToken(userDetails);

        System.out.println(">>> jwtToken: " + token);
        System.out.println(">>> jwtTokenExpiryDate: " + jwtUtil.getExpirationDateFromToken(token));

        return ResponseEntity.ok(new JwtResponse(token, userSvc.getUser(email).get().getUsername()));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            // System.out.println(">>>>> bcrypt password? " +
            // passwordEncoder.encode(password));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
