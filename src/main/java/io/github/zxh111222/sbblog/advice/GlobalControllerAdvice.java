package io.github.zxh111222.sbblog.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("requestURI")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

}
