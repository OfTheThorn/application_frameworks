package com.matthiasvdd.opdracht.controllers;

import com.matthiasvdd.opdracht.data.repositories.ProductRepository;
import com.matthiasvdd.opdracht.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    @Autowired
    ProductRepository repo;

    @GetMapping("/detail/{id}")
    public ModelAndView productDetail(@PathVariable(value = "id") int id){
        Product p = repo.findById(id);
        if(p == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        ModelAndView mav = new ModelAndView("detail");
        mav.addObject("product",p);
        return mav;
    }
}
