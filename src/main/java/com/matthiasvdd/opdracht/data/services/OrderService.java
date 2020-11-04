package com.matthiasvdd.opdracht.data.services;

import com.matthiasvdd.opdracht.data.repositories.OrderProductRepository;
import com.matthiasvdd.opdracht.data.repositories.OrderRepository;
import com.matthiasvdd.opdracht.data.repositories.ProductRepository;
import com.matthiasvdd.opdracht.models.Order;
import com.matthiasvdd.opdracht.models.OrderProduct;
import com.matthiasvdd.opdracht.models.Product;
import com.matthiasvdd.opdracht.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    ProductRepository productRepository;

    public Order findOrderForUser(boolean inCart) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Order o = orderRepository.findByUserIdAndInShoppingCart(user.getId(), inCart);
        if (o == null){
            o = new Order();
            o.setUser(user);
            orderRepository.save(o);
        }
        return o;
    }

    public void addProductToOrder(Product product) {
        Order o = findOrderForUser(true);
        OrderProduct orderProduct = new OrderProduct(o, product);
        orderProductRepository.save(orderProduct);
    }

    public void changeProductQuantity(Product product, int qty) {
        Order o = findOrderForUser(true);
        OrderProduct orderProduct = new OrderProduct(o, product);
        orderProduct.setAmount(qty);
        orderProductRepository.save(orderProduct);
    }

    public void removeProduct(Product product){
        Order o = findOrderForUser(true);
        OrderProduct op = orderProductRepository.findByProductAndOrder(product, o);
        orderProductRepository.delete(op);
    }

    public ArrayList<String> confirmOrder(){
        AtomicBoolean confirmed = new AtomicBoolean(true);
        ArrayList<String> errors = new ArrayList<>();
        Order o = findOrderForUser(true);
        Set<OrderProduct> op = o.getOrderProducts();

        op.stream().forEach(orderProduct -> {
            if (orderProduct.getProduct().getAmountAvailable() < orderProduct.getAmount()){
                errors.add(orderProduct.getProductName() + " only " + orderProduct.getProduct().getAmountAvailable() + " bottles available.");
                confirmed.set(false);
            }
        });
        if(!errors.isEmpty())
            return errors;

        o.setOrderConfirmed(true);
        o.setInShoppingCart(false);
        op.forEach(orderProduct -> {
            orderProduct.getProduct().setAmountAvailable(orderProduct.getProduct().getAmountAvailable() - orderProduct.getAmount());
            productRepository.save(orderProduct.getProduct());
        });
        orderRepository.save(o);
        return null;
    }
}
