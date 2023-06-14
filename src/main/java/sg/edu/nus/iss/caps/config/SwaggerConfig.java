package sg.edu.nus.iss.caps.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Cooper Liu
 * @Description: Swagger Configuration Class
 * @Date: Created at 9:58 2023/6/14
 * @Modified by:
 */

@Configuration
public class SwaggerConfig {

    /**
     * Config Swagger
     * @return OpenAPI object
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("CAPS API Documentation")
                        .description("This is a API document of CAPS-System.")
                        .contact(new Contact()
                                .name("Cooper")
                                .url("https://github.com/Archett0")
                                .email("e1068644@u.nus.edu"))
                        .version("0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Docs for ref")
                        .url("https://github.com/Archett0/CAPS_Backend#readme"));
    }
}
