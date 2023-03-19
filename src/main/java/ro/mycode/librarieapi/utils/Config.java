package ro.mycode.librarieapi.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Config {



    @Bean
    ObjectMapper objectMapper(){


        ObjectMapper mapper= JsonMapper.builder()
                .addModule(new JavaTimeModule()).build();

        return mapper;
    }


}
