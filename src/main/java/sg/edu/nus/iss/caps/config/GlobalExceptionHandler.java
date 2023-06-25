package sg.edu.nus.iss.caps.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sg.edu.nus.iss.caps.common.R;

/**
 * @Author: Cooper Liu
 * @Description:
 * @Date: Created at 23:44 2023/6/14
 * @Modified by:
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Intercept all exceptions
     * @param e Exception
     * @return Common response
     */
    @ExceptionHandler
    public R handlerException(Exception e) {

        // Print exception stack
        e.printStackTrace();

        // Generate response 'failed'
        if (e.getMessage().indexOf(':') == -1) {
            return R.error(e.getMessage());
        }
        else {
            return R.error(e.getMessage().substring(e.getMessage().indexOf(':') + 1));
        }
    }
}
