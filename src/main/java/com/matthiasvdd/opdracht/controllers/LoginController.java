package com.matthiasvdd.opdracht.controllers;

import com.matthiasvdd.opdracht.models.User;
import com.matthiasvdd.opdracht.validation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin() {
        return "login.html";
    }

    @GetMapping(value = "/register")
    public String showRegister(Model model) {
        User u = new User();
        model.addAttribute("user", u);
        return "register";
    }

    @PostMapping(value = "/register")
    public ModelAndView register(@Valid User user, BindingResult bindingResult) {
        System.out.println("test0");
        ModelAndView mav = new ModelAndView("/register");
        if (bindingResult.hasErrors()) {
            System.out.println("Bindingresult " + bindingResult.getAllErrors().toString());
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }
        System.out.println("Test1");
        try {
            User registered = userService.registerUser(user);
            System.out.println("Registered " + registered);
            mav.addObject("success", registered);
            System.out.println("Test2");
            return mav;
        } catch (Exception uaeEx) {
            System.out.println("Exception in here: " + uaeEx.getMessage());
            ObjectError objectError = new ObjectError("email",uaeEx.getMessage());
            bindingResult.rejectValue("email", "errors", uaeEx.getMessage());
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }
    }
}
