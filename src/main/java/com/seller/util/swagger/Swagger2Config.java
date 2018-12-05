package com.seller.util.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-08-21 14:13
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {
    //访问路径 /swagger-ui.html
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.seller.controller"))//根据自己项目修改
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        //访问地址 /swagger-ui.html
        return new ApiInfoBuilder()
                .title("RESTful APIs ")
                .description("Restful API文档")
                .version("1.0")
                .build();
    }
}
