package com.matthiasvdd.opdracht.controllers;


import com.matthiasvdd.opdracht.data.repositories.ProductRepository;
import com.matthiasvdd.opdracht.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class HomeController {
    @Autowired
    ProductRepository repo;

    @GetMapping(value = "/")
    public ModelAndView showIndex(@RequestParam(value = "searchTag", required = false) String searchTag) {
        ArrayList<Product> prodList;
        if(searchTag == null || searchTag.isEmpty())
            prodList = new ArrayList<>(repo.findAll());
        else
            prodList = new ArrayList<>(repo.findByNameContainingIgnoreCase(searchTag));
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("products",prodList);
        return mav;
    }
}
