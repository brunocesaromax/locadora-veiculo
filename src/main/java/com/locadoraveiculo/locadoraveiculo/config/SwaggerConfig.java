package com.locadoraveiculo.locadoraveiculo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String VEHICLE_RENTAL = "vehicle rental";

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.locadoraveiculo.locadoraveiculo.controller"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(VEHICLE_RENTAL, "Vehicle Rental"))
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
                .title("Vehicle Rental API")
                .description("Vehicle Rental Management REST API")
                .version("1.0-SNAPSHOT")
                .build();
    }
}