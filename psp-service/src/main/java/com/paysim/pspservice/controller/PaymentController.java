package com.paysim.pspservice.controller;

import com.paysim.pspservice.dto.PaymentRequest;
import com.paysim.pspservice.dto.PaymentResponse;
import com.paysim.pspservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse processPayment(@Valid @RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}
