package sg.edu.nus.iss.caps.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sg.edu.nus.iss.caps.interceptors.AuthenticationInterceptor;

public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authenticationInterceptor);
    }
}
