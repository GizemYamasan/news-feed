package online.csdm.newsfeed;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("online.csdm.newsfeed.controller"))
          .paths(PathSelectors.ant("/feeds**"))     
          .build()
          .apiInfo(apiInfo());                                           
    }
    
    private ApiInfo apiInfo() {
    	Contact contact = new Contact("Gizem Yamasan", "www.csdm.online", "gizemyamasan@gmail.com");
        return new ApiInfo(
          "CDSM Feed Challenge API", 
          "fetch feeds from multiple source and distribute as a single API.",
          "0.0.1-SNAPSHOT",
          "Terms of Service", 
          contact, 
          "License of API", 
          "API license URL", 
          Collections.emptyList());
    }
}