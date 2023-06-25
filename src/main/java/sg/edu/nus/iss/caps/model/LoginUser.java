package sg.edu.nus.iss.caps.model;

import lombok.Data;

/**
 * @Author: Cooper Liu
 * @Description: Info needed for the login action
 * @Date: Created at 18:04 2023/6/18
 * @Modified by:
 */

@Data
public class LoginUser {
    private Long userId;
    private String username;
    private String password;
    private String role;
}
