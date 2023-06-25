package sg.edu.nus.iss.caps.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 14:36 2023/6/25
 * @Modified by:
 */
public class EncryptPassword {
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String EncodePassword(String org_password) {
        return passwordEncoder().encode(org_password);
    }
}
