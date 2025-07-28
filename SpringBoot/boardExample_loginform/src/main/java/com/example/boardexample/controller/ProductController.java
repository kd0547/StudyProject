package com.example.boardexample.controller;

import com.example.boardexample.dto.ProductDto;
import com.example.boardexample.service.ProductService;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/product/view")
    public String view(Model model) {

        List<ProductDto> list = productService.productList();
        model.addAttribute("productList",list);
        model.addAttribute("row",5);
        model.addAttribute("col",4);

        return "product/list";
    }
}
