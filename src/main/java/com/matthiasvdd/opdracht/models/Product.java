package com.matthiasvdd.opdracht.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// Products will be different types of alcohol
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Name cannot be empty.")
    @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
    private String name;
    @NotNull(message = "Brand cannot be empty.")
    @Size(min = 3, max = 20, message = "Brand should be between 3 and 20 characters")
    private String brand;

    private CategoryEnum category;


    private String description;
    @Min(value = 0, message = "Value needs to be minimum 0")
    private int amountAvailable;
    @DecimalMin(value = "0", message = "Value needs to be bigger than 0")
    private double price;

    public Product() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public double getPrice() {
        return price;
    }
}
