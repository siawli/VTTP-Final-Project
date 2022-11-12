package VTTP.FinalProject.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import VTTP.FinalProject.filters.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // @Autowired
    // private JwtUserDetailsService userSvc;

    private JwtRequestFilter jwtFilter;
    @Autowired
    public void setJwtFilter(JwtRequestFilter filter) {
        this.jwtFilter = filter;
    }

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf()
        .disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .authorizeRequests()
        .antMatchers("/assets/**", "/polyfills.b9005cfd1a54175e.js", "/main.09753e618dfe4822.js", "/styles.43aecdfb5c9c28ca.css", "/runtime.e3f5fe3eca1bd5d1.js", "/favicon.ico", "/", "/login", "/signUp", "/authenticate") // routes indicated here is not protected
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public DaoAuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         
    //     authProvider.setUserDetailsService(userSvc);
    //     authProvider.setPasswordEncoder(passwordEncoder());
     
    //     return authProvider;
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // }


    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    //     PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    //     UserDetails user = User.withUsername("spring")
    //         .password(encoder.encode("secret"))
    //         .roles("USER")
    //         .build();
    //     return new InMemoryUserDetailsManager(user);
    // }
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return NoOpPasswordEncoder.getInstance();
    // }


    
}
