package com.paysim.clientapi.controller;

import com.paysim.clientapi.dto.CardPaymentRequest;
import com.paysim.clientapi.dto.PaymentResultResponse;
import com.paysim.clientapi.service.PaymentClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final PaymentClientService paymentClientService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public PaymentResultResponse defineCard(@Valid @RequestBody CardPaymentRequest request) {
        return paymentClientService.submitPayment(request);
    }
}
