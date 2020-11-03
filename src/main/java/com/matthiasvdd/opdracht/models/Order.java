package com.matthiasvdd.opdracht.models;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

// Had to change tablename because ORDER is a protected keyword
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private boolean inShoppingCart;
    @Temporal(TemporalType.TIMESTAMP)
    private Date shoppingCartDate;

    private boolean orderConfirmed;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderConfirmedDate;

    @ManyToMany()
    @JoinTable(
            name = "Order_Product",
            joinColumns = { @JoinColumn(name = "order_id")},
            inverseJoinColumns = { @JoinColumn(name = "product_id")}
    )
    private List<Product> products;

    // Transient because no need to store in DB
    /*@Transient
    public Double getTotalOrderPrice() {
        double sum = 0.0;
        for (OrderProduct op : orderProducts) {
            sum += op.getTotalPrice();
        }
        return sum;
    }

    @Transient
    public int getNumberOfProducts() {
        r
*/
}
