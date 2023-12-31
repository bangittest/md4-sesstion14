package com.ra.controller;

import com.ra.model.entity.User;
import com.ra.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String login(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "admin/login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("user") User user, Model model) {
        User authent = userService.checkLogin(user.getEmail(), user.getPassword());

        if (authent != null) {
            model.addAttribute("user", authent);
            session.setAttribute("user",user);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(){
        session.removeAttribute("user");
        return "redirect:/";
    }
}
