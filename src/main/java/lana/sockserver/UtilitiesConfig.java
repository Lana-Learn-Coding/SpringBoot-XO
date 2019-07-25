package lana.sockserver;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UtilitiesConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
