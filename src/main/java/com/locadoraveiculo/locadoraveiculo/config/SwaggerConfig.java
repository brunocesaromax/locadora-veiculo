package com.locadoraveiculo.locadoraveiculo.config;

import com.locadoraveiculo.locadoraveiculo.service.LocaleMessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {

    public static final String ACCESSORY_API_TAG = "Acessórios";
    public static final String CAR_API_TAG = "Carros";
    public static final String CAR_MODEL_API_TAG = "Modelo de Carros";
    public static final String CUSTOMER_API_TAG = "Clientes";
    public static final String DRIVER_API_TAG = "Motoristas";
    public static final String EMPLOYEE_API_TAG = "Funcionários";
    public static final String PRODUCER_API_TAG = "Fabricantes";
    public static final String RENT_API_TAG = "Aluguéis";
    public static final String SALE_API_TAG = "Vendas";
    public static final String VEHICLE_API_TAG = "Veículos";

    private final LocaleMessageSource localeMessageSource;

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(localeMessageSource.getMessage("Swagger.apis.basePackage")))
                .paths(PathSelectors.any())
                .build()
//                .tags(
//                        new Tag(localeMessageSource.getMessage("Swagger.tag.description"),
//                                localeMessageSource.getMessage("Swagger.tag.description"))
//                )
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
                .title(localeMessageSource.getMessage("Swagger.api.title"))
                .description(localeMessageSource.getMessage("Swagger.api.description"))
                .version(localeMessageSource.getMessage("Swagger.api.currentVersion"))
                .build();
    }
}