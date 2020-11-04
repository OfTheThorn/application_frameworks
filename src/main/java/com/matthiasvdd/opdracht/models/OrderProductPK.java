package com.matthiasvdd.opdracht.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderProductPK implements Serializable {
    public OrderProductPK(int order_id, int product_id) {
        this.order_id = order_id;
        this.product_id = product_id;
    }

    public OrderProductPK() {
    }

    @Column(name = "order_id")
    private int order_id;

    @Column(name = "product_id")
    private int product_id;
}
