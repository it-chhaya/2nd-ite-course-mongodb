package dev.chanchhaya.course;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.Properties;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@Slf4j
public class CoursesService {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(CoursesService.class, args);

        String activeProfile = ctx.getEnvironment().getProperty("spring.profiles.active");
        String mongoDdUri = ctx.getEnvironment().getProperty("spring.data.mongodb.uri");

        log.info("{}", activeProfile);
        log.info("{}", mongoDdUri);

    }



}
