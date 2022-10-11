package VTTP.FinalProject.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import VTTP.FinalProject.configurations.JwtUtil;
import VTTP.FinalProject.services.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtUserDetailsService jwtUserDetailsSvc;
    @Autowired
    public void setJwtUserDetailsSvc(JwtUserDetailsService service) {
        this.jwtUserDetailsSvc = service;
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        final String requestTokenHeader = request.getHeader("Authorization");
        // System.out.println(">> requestTokenHeader filter: " + requestTokenHeader);

        String username = null;
        String jwtToken = null;

        // JWT token is in the form of "Bearer token".
        // Remove Bearer word and get only the token
        // System.out.println("Bearer exists? " + requestTokenHeader.startsWith("Bearer "));
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            // System.out.println(">>>> in filter: ");
            jwtToken = requestTokenHeader.substring(7);
            // System.out.println(">>>> filter jwt token: " + jwtToken);
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException ex) {
                System.out.println("Unable to get JWT token");
            } catch (ExpiredJwtException ex) {
                System.out.println("JWT token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // Once we get the token, validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailsSvc.loadUserByUsername(username);

            // if token is valid, configure Spring Security to manually set authentication
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

                // After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
        
    }
    
}
