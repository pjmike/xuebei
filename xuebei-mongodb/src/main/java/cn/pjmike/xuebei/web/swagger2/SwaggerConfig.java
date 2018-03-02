package cn.pjmike.xuebei.web.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 *
 * @author pjmike
 * @create 2018-02-16 14:35
 */
@Component
@EnableSwagger2
@EnableWebMvc
@ComponentScan({"cn.pjmike.xuebei.web.controller","cn.pjmike.xuebei.web.chat.controller"})
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("利用Swagger2搭建学呗系统的API文档")
                .description("学呗项目的API接口文档")
                .termsOfServiceUrl("pjmike.github.io")
                .version("1.0")
                .build();
    }
}

