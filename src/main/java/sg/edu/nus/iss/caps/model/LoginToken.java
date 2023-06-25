package sg.edu.nus.iss.caps.model;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: Cooper Liu
 * @Description: Represent the JWT token a user gets
 * @Date: Created at 18:06 2023/6/18
 * @Modified by:
 */

@Data
@Builder
public class LoginToken {
    private final String accessToken;
}
