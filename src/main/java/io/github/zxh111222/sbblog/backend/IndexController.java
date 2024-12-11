package io.github.zxh111222.sbblog.backend;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("BackendIndexController")
@RequestMapping("backend")
public class IndexController {
    @GetMapping()
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "backend/index";
    }
}
