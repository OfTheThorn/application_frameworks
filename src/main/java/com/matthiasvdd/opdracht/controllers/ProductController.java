package com.matthiasvdd.opdracht.controllers;

import com.matthiasvdd.opdracht.data.repositories.ProductRepository;
import com.matthiasvdd.opdracht.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    @Autowired
    ProductRepository repo;

    @GetMapping("/detail/{id}")
    public ModelAndView productDetail(@PathVariable(value = "id") int id){
        //TODO: Remove non numbers?
        Product p = repo.findById(id);
        if(p == null)
            return null;

        ModelAndView mav = new ModelAndView("detail");
        mav.addObject("product",p);
        return mav;
    }
}
