package com.matthiasvdd.opdracht.data.repositories;

import com.matthiasvdd.opdracht.models.Order;
import com.matthiasvdd.opdracht.models.OrderProduct;
import com.matthiasvdd.opdracht.models.OrderProductPK;
import com.matthiasvdd.opdracht.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
    OrderProduct findByProductAndOrder(Product productId, Order orderId);
}
