package com.matthiasvdd.opdracht.controllers;

import com.matthiasvdd.opdracht.data.repositories.ProductRepository;
import com.matthiasvdd.opdracht.data.services.OrderService;
import com.matthiasvdd.opdracht.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("isAuthenticated()")
public class OrderController {


    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/shopping-cart")
    public ModelAndView showShoppingCart(RedirectAttributes redirectAttributes) {
        Order o = orderService.findOrderForUser(true);
        Set<OrderProduct> orderProducts = o.getOrderProducts();
        List<OrderProduct> orderProductsArrayList = orderProducts.stream().sorted(Comparator.comparing(OrderProduct::getProductName)).collect(Collectors.toList());
        double total = orderProducts.parallelStream()
                .map(OrderProduct::getTotalPrice)
                .reduce(0D, Double::sum);
        ModelAndView mav = new ModelAndView("/shoppingcart");
        mav.addObject("orderProducts", orderProductsArrayList);
        mav.addObject("total", total);
        return mav;
    }

    @GetMapping(value = "/shopping-cart/add/{id}")
    public String addToCart(@PathVariable("id") int drinkId, RedirectAttributes redirectAttributes) {
        Product p = productRepository.findById(drinkId);
        orderService.addProductToOrder(p);
        redirectAttributes.addFlashAttribute("success", "Product has been added to cart");
        return "redirect:/shopping-cart";
    }

    @RequestMapping(value = "/shopping-cart/edit/", method = RequestMethod.POST)
    public String editQuantity(@RequestParam(name = "qty") int qty, @RequestParam(name = "productId") int productId, RedirectAttributes redirectAttributes) {
        Product p = productRepository.findById(productId);
        if (p.getAmountAvailable() < qty) {
            redirectAttributes.addFlashAttribute("error", "Insufficient bottles available");
            return "redirect:/shopping-cart";
        }

        orderService.changeProductQuantity(p, qty);
        redirectAttributes.addFlashAttribute("success", "Amount modified");
        return "redirect:/shopping-cart";
    }

    @RequestMapping(value = "/shopping-cart/delete/", method = RequestMethod.POST)
    public String removeProduct(@RequestParam(name = "productId") int productId, RedirectAttributes redirectAttributes) {
        Product p = productRepository.findById(productId);
        orderService.removeProduct(p);
        return "redirect:/shopping-cart";
    }

    @RequestMapping(value = "/shopping-cart/confirm", method = RequestMethod.POST)
    public String confirmOrder(RedirectAttributes redirectAttributes) {
        ArrayList<String> errors = orderService.confirmOrder();
        if (errors != null)
            redirectAttributes.addFlashAttribute("errors", errors);
        else
            redirectAttributes.addFlashAttribute("success", "Order has been confirmed");
        return "redirect:/shopping-cart";
    }

}