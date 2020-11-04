package com.matthiasvdd.opdracht.models;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Had to change tablename because ORDER is a protected keyword
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private boolean inShoppingCart = true;
    @Temporal(TemporalType.TIMESTAMP)
    private Date shoppingCartDate;

    private boolean orderConfirmed = false;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderConfirmedDate;

    @OneToMany(mappedBy = "order")
    Set<OrderProduct> orderProducts = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    public Order() {
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public boolean isInShoppingCart() {
        return inShoppingCart;
    }

    public void setInShoppingCart(boolean inShoppingCart) {
        this.inShoppingCart = inShoppingCart;
    }

    public Date getShoppingCartDate() {
        return shoppingCartDate;
    }

    public void setShoppingCartDate(Date shoppingCartDate) {
        this.shoppingCartDate = shoppingCartDate;
    }

    public boolean isOrderConfirmed() {
        return orderConfirmed;
    }

    public void setOrderConfirmed(boolean orderConfirmed) {
        this.orderConfirmed = orderConfirmed;
    }

    public Date getOrderConfirmedDate() {
        return orderConfirmedDate;
    }

    public void setOrderConfirmedDate(Date orderConfirmedDate) {
        this.orderConfirmedDate = orderConfirmedDate;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }
    /*
    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0.0;
        for (Product op : products) {
            sum += op.getPrice();
        }
        return sum;
    }

    @Transient
    public int getNumberOfProducts() {
        r
 */
}
