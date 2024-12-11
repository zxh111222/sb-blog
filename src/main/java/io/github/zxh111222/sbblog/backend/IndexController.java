package io.github.zxh111222.sbblog.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("BackendIndexController")
@RequestMapping("backend")
public class IndexController {
    @GetMapping()
    public String index() {
        return "backend/index";
    }
}
