package com.php25.qiuqiu.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penghuiping
 * @date 2022/11/6 12:44
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI custOpenApi() {
        //指定使用Swagger2规范
        return new OpenAPI()
                .info(new Info()
                        .description("qiuqiu_admin APIs")
                        .version("1.0"));
    }
}
