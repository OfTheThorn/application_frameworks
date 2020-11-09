package com.matthiasvdd.opdracht.controllers;

import com.matthiasvdd.opdracht.models.User;
import com.matthiasvdd.opdracht.validation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
        ModelAndView mav = new ModelAndView("/register");
        if (bindingResult.hasErrors()) {
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }
        try {
            User registered = userService.registerUser(user);
            mav.setViewName("/login");
            mav.addObject("success", registered);
            return mav;
        } catch (Exception uaeEx) {
            bindingResult.rejectValue("email", "errors", uaeEx.getMessage());
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }
    }

    @GetMapping(value = "/profile")
    public String showProfile(Model model, @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        User u = (User) authentication.getPrincipal();
        model.addAttribute("user", u);
        return "profile";
    }

    @RequestMapping(value = "/profile/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editProfile(@PathVariable("id") int id, @Valid User user, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/profile");

        if (bindingResult.hasErrors()) {
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User edited = userService.editUser(user, id);
        mav.addObject("success", true);
        return mav;
    }
}
