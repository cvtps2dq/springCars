package ru.cv2.springcars.config;

import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationConfigurable {
    @Configuration
    public class AppConfig {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        public Faker faker() {
            return new Faker();
        }
    }
}
