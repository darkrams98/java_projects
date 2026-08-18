package com.paysim.clientapi.controller;

import com.paysim.clientapi.dto.ProductResponse;
import com.paysim.clientapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProductResponse> getProducts() {
        return productService.getAllProducts();
    }
}
