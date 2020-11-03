package com.matthiasvdd.opdracht.data.repositories;

import com.matthiasvdd.opdracht.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
