package com.matthiasvdd.opdracht.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_product")
public class OrderProduct {

    @EmbeddedId
    private OrderProductPK id;

    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name="amount")
    private int amount;

    public OrderProduct() {
    }

    public OrderProduct(Order order, Product product) {
        this.id = new OrderProductPK(order.getId(), product.getId());
        this.order = order;
        this.product = product;
        this.amount = 1;
    }

    public String getProductName(){
        return this.product.getName();
    }

    public OrderProductPK getId() {
        return id;
    }

    public void setId(OrderProductPK id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice(){
        return product.getPrice() * amount;
    }

}
