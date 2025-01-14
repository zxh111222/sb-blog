package io.github.zxh111222.sbblog.controller;

import io.github.zxh111222.sbblog.dto.UserDTO;
import io.github.zxh111222.sbblog.exception.BusinessException;
import io.github.zxh111222.sbblog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("dashboard")
    @PreAuthorize("isAuthenticated()")
    public String dashboard(){
        return "/user/dashboard";
    }

    @GetMapping("register")
    public String enroll(Model model){
        model.addAttribute("user", new UserDTO());
        return "/user/register";
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute("user") UserDTO user, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "/user/register";  // 如果有错误，返回注册页面
//        }
        try {
            String email = user.getEmail();
            String username = user.getName();

            if (username == null || username.isEmpty()) {
                username = email.split("@")[0];
            }

            userService.save(user);
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getDescription());
            return "/user/register";
        }


        return "redirect:/";
    }

}