package sg.edu.nus.iss.caps.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Cooper Liu
 * @Description: To retrieve secret from application.yml
 * @Date: Created at 19:24 2023/6/18
 * @Modified by:
 */

@Data
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    /**
     * Secret key sed to generate JWT
     */
    private String secretKey;
}
