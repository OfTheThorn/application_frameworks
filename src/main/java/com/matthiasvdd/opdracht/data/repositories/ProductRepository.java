package com.matthiasvdd.opdracht.data.repositories;

import com.matthiasvdd.opdracht.models.CategoryEnum;
import com.matthiasvdd.opdracht.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategory(CategoryEnum category);
    List<Product> findByNameContainingIgnoreCaseAndCategory(String name, CategoryEnum categoryEnum);
    List<Product> findAll();
    Product findById(int id);

}
