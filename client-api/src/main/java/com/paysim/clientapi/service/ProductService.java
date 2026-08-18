package com.paysim.clientapi.service;

import com.paysim.clientapi.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private static final List<ProductResponse> CATALOG = List.of(
            new ProductResponse(1L, "Wireless Mouse", "Ergonomic 2.4GHz wireless mouse", BigDecimal.valueOf(250000)),
            new ProductResponse(2L, "Mechanical Keyboard", "RGB hot-swappable mechanical keyboard", BigDecimal.valueOf(1200000)),
            new ProductResponse(3L, "USB-C Hub", "7-in-1 USB-C hub with HDMI", BigDecimal.valueOf(450000)),
            new ProductResponse(4L, "Noise Cancelling Headphones", "Over-ear ANC headphones", BigDecimal.valueOf(2100000))
    );

    public List<ProductResponse> getAllProducts() {
        return CATALOG;
    }
}
