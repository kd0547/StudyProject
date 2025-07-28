package com.example.boardexample.service;

import com.example.boardexample.dto.ProductDto;
import com.example.boardexample.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    public List<ProductDto> productList() {
        return productRepository.findProducts()
                .stream()
                .map(ProductDto::createProductDto)
                .collect(Collectors.toList());

    }
}
