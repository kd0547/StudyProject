package com.example.boardexample.dto;

import com.example.boardexample.entity.Category;
import com.example.boardexample.entity.Product;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;

    private String name;

    private String description;

    private double price;

    private int count;

    private Category category;

    public ProductDto (){

    }
    public ProductDto (Long id,String name,String description,double price ,Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public ProductDto (Long id,String name,String description,double price ,Category category,int count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.count = count;
    }


    public static ProductDto createProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory()
        );
    }

}
