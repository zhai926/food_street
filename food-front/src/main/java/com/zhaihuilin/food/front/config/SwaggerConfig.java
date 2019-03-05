//package com.zhaihuilin.food.front.config;
//
//import io.swagger.annotations.ApiOperation;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Swagger 配置类
// * Created by zhaihuilin on 2019/1/9 13:28.
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//     public Docket createRestApi(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                // 扫描所有有注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any())
//                .build();
//     }
//
//    /**
//     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
//    * 访问地址：http://项目实际地址/swagger-ui.html
//    * @return
//   */
//     private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("美食街app相关API")
//                .description("美食街app相关API")
//                .termsOfServiceUrl("http://www.baidu.com") //访问地址
//                .version("2.2.2")
//                .build();
//     }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
