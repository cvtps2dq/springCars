package ru.cv2.springcars.config;

import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.cv2.springcars.security.UserDetalServiceImpl;

@Configuration
public class ApplicationConfigurable {
    private final UserDetalServiceImpl userDetailsService;

    @Autowired
    public ApplicationConfigurable(UserDetalServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        public Faker faker() {
            return new Faker();
        }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    }

