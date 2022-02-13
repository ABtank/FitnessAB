package ru.abtank.fitnessab.config;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static ru.abtank.fitnessab.constants.SwaggerConstant.*;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket apiDocket(){
        return new Docket(SWAGGER_2)
                .groupName(API_TAG)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()));
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(API_TITLE, API_DESCRIPTION,API_VERSION,TERM_OF_SERVICE,contact()
                ,LICENSE,LICENSE_URL, Collections.emptyList());
    }

    private Contact contact() {
        return new Contact(CONTACT_NAME,CONTACT_URL,CONTACT_EMAIL);
    }

    private ApiKey apiKey(){
        return new ApiKey(SECURITY_REFERENCE,AUTHORIZATION, SecurityScheme.In.HEADER.toString());
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(securityReferences()).build();
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope(AUTHORIZATION_SCOPE,AUTHORIZATION_DESCRIPTION)};
        return singletonList(new SecurityReference(SECURITY_REFERENCE,authorizationScopes));
    }

}

