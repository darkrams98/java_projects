package com.paysim.clientapi.service;

import com.paysim.clientapi.dto.CardPaymentRequest;
import com.paysim.clientapi.dto.PaymentResultResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentClientService {

    private final RestTemplate restTemplate;
    private final String pspBaseUrl;

    public PaymentClientService(RestTemplate restTemplate,
                                 @Value("${services.psp.base-url}") String pspBaseUrl) {
        this.restTemplate = restTemplate;
        this.pspBaseUrl = pspBaseUrl;
    }

    public PaymentResultResponse submitPayment(CardPaymentRequest request) {
        return restTemplate.postForObject(pspBaseUrl + "/api/payments", request, PaymentResultResponse.class);
    }
}
