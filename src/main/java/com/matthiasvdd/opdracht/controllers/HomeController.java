package com.matthiasvdd.opdracht.controllers;


import com.matthiasvdd.opdracht.data.converters.CategoryConverter;
import com.matthiasvdd.opdracht.data.repositories.ProductRepository;
import com.matthiasvdd.opdracht.models.CategoryEnum;
import com.matthiasvdd.opdracht.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ProductRepository repo;

    @GetMapping(value = "/")
    public ModelAndView showIndex(@RequestParam(value = "searchTag", required = false) String searchTag, @RequestParam(value = "filter", required = false) String filter) {
        ArrayList<Product> prodList;
        if(searchTag == null && filter == null)
            prodList = new ArrayList<>(repo.findAll());
        else if(searchTag != null && filter == null)
            prodList = new ArrayList<>(repo.findByNameContainingIgnoreCase(searchTag));
        else if(searchTag == null){
            CategoryConverter categoryConverter = new CategoryConverter();
            CategoryEnum categoryEnum = categoryConverter.convertToEntityAttribute(filter);
            prodList = new ArrayList<>(repo.findByCategory(categoryEnum));
        }
        else {
            CategoryConverter categoryConverter = new CategoryConverter();
            CategoryEnum categoryEnum = categoryConverter.convertToEntityAttribute(filter);
            prodList = new ArrayList<>(repo.findByNameContainingIgnoreCaseAndCategory(searchTag, categoryEnum));
        }
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("products",prodList);
        return mav;
    }
}
