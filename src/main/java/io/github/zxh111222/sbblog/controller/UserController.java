package io.github.zxh111222.sbblog.controller;

import io.github.zxh111222.sbblog.dto.PasswordResetEmailDTO;
import io.github.zxh111222.sbblog.dto.UserDTO;
import io.github.zxh111222.sbblog.entity.User;
import io.github.zxh111222.sbblog.service.UserService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender sender;

    @Autowired
    private View error;

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
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, HttpServletRequest httpServletRequest) throws ServletException {        // 根据邮箱查询数据库
        User existingUser =  userService.findByEmail(userDTO.getEmail());
        if (existingUser != null){
            result.rejectValue("email", "exist", "该邮箱已被注册");
        }
        if (result.hasErrors()){
            return "/user/register";
        }
        userService.save(userDTO);

        // 帮该用户自动登录
        httpServletRequest.login(userDTO.getEmail(), userDTO.getPassword());

        return "redirect:/";
    }

    @GetMapping("password-reset")
    public String showPasswordResetForm(Model model){
        model.addAttribute("passwordResetEmail", new PasswordResetEmailDTO());
        return "user/password-reset";
    }

    @PostMapping
    public String passwordReset(
            @Valid @ModelAttribute("passwordResetEmail") PasswordResetEmailDTO passwordResetEmailDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            HttpServletRequest httpServletRequest    ) throws Exception {
        // 检查邮箱是否存在
        User existingUser = userService.findByEmail(passwordResetEmailDTO.getEmail());
        if (existingUser == null){
            result.rejectValue("email", "non-existent", "该邮箱不存在");
        }

        if (result.hasErrors()){
            return "user/password-reset";
        }

        // todo: 发送邮件
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(new InternetAddress("3345973813@qq.com", "客服"));
        helper.setSubject("重置密码");
        helper.setTo(passwordResetEmailDTO.getEmail());
        String scheme = httpServletRequest.getScheme();
        String serverName = httpServletRequest.getServerName();
        int port = httpServletRequest.getServerPort();
        String baseUrl = scheme + "://" + serverName + ":" + port;
        helper.setText("""
                <html>
                    <body>
                        <p>点击以下链接进行密码重置</p>
                        <a href='%s/user/do-password-reset?token=xxx123xxx'>重置密码</a>                        <p>链接将在 30 分钟后失效，请尽快操作</p>
                    </body>
                </html>
                """.formatted(baseUrl), true);

        sender.send(message);
        redirectAttributes.addFlashAttribute("success", "密码重置邮箱已发送，请注意查收");

        return "redirect:/user/password-reset";
    }

    @GetMapping("do-password-reset")
    public String showPasswordResetForm(@RequestParam String token, Model model) {
        // 获取 token 进行校验
        if ("xxx123xxx_0".equals(token)) {
            model.addAttribute("error", "token 已过期");
        } else if (!"xxx123xxx".equals(token)) {
            model.addAttribute("error", "token 不存在");
        }
        return "user/do-password-reset";
    }

}
