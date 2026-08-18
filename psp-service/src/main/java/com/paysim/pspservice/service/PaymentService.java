package com.paysim.pspservice.service;

import com.paysim.pspservice.document.PaymentLog;
import com.paysim.pspservice.dto.PaymentRequest;
import com.paysim.pspservice.dto.PaymentResponse;
import com.paysim.pspservice.dto.ShaparakRequest;
import com.paysim.pspservice.dto.ShaparakResponse;
import com.paysim.pspservice.repository.PaymentLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final PaymentLogRepository paymentLogRepository;
    private final String shaparakBaseUrl;

    public PaymentService(RestTemplate restTemplate,
                           PaymentLogRepository paymentLogRepository,
                           @Value("${services.shaparak.base-url}") String shaparakBaseUrl) {
        this.restTemplate = restTemplate;
        this.paymentLogRepository = paymentLogRepository;
        this.shaparakBaseUrl = shaparakBaseUrl;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        String referenceId = UUID.randomUUID().toString();
        String masked = maskCard(request.cardNumber());

        PaymentLog paymentLog = PaymentLog.builder()
                .referenceId(referenceId)
                .maskedCardNumber(masked)
                .cardName(request.cardName())
                .amount(request.amount())
                .productId(request.productId())
                .status("PENDING")
                .createdAt(Instant.now())
                .build();
        paymentLogRepository.save(paymentLog);

        ShaparakRequest shaparakRequest = new ShaparakRequest(referenceId, request.cardNumber(), request.cardName(), request.amount());

        PaymentResponse result;
        try {
            ShaparakResponse shaparakResponse = restTemplate.postForObject(
                    shaparakBaseUrl + "/api/shaparak/process",
                    shaparakRequest,
                    ShaparakResponse.class
            );

            if (shaparakResponse == null) {
                paymentLog.setStatus("FAIL");
                result = new PaymentResponse("FAIL", referenceId, "Empty response from Shaparak");
            } else {
                paymentLog.setStatus(shaparakResponse.status());
                paymentLog.setShaparakReferenceId(shaparakResponse.referenceId());
                result = new PaymentResponse(shaparakResponse.status(), referenceId, shaparakResponse.message());
            }
            log.info("Payment {} processed with status {}", referenceId, result.status());
        } catch (Exception ex) {
            paymentLog.setStatus("FAIL");
            result = new PaymentResponse("FAIL", referenceId, "Payment gateway error");
            log.error("Payment {} failed calling Shaparak: {}", referenceId, ex.getMessage());
        }

        paymentLog.setCompletedAt(Instant.now());
        paymentLogRepository.save(paymentLog);

        return result;
    }

    private String maskCard(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 10) {
            return "****";
        }
        return cardNumber.substring(0, 6) + "******" + cardNumber.substring(cardNumber.length() - 4);
    }
}
