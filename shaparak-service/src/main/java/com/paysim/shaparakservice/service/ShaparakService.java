package com.paysim.shaparakservice.service;

import com.paysim.shaparakservice.document.TransactionLog;
import com.paysim.shaparakservice.dto.BankRequest;
import com.paysim.shaparakservice.dto.BankResponse;
import com.paysim.shaparakservice.dto.ShaparakRequest;
import com.paysim.shaparakservice.dto.ShaparakResponse;
import com.paysim.shaparakservice.repository.TransactionLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShaparakService {

    private final RabbitTemplate rabbitTemplate;
    private final BankRoutingService bankRoutingService;
    private final TransactionLogRepository transactionLogRepository;

    public ShaparakResponse routeToBank(ShaparakRequest request) {
        String referenceId = UUID.randomUUID().toString();
        String bin = request.cardNumber().substring(0, 6);
        String targetQueue = bankRoutingService.resolveQueue(request.cardNumber());

        TransactionLog transactionLog = TransactionLog.builder()
                .pspReferenceId(request.pspReferenceId())
                .referenceId(referenceId)
                .bin(bin)
                .targetQueue(targetQueue)
                .maskedCardNumber(maskCard(request.cardNumber()))
                .amount(request.amount())
                .status("PENDING")
                .createdAt(Instant.now())
                .build();
        transactionLogRepository.save(transactionLog);

        BankRequest bankRequest = new BankRequest(referenceId, request.cardNumber(), request.cardName(), request.amount());

        ShaparakResponse response;
        try {
            log.info("Routing transaction {} (BIN {}) to queue {}", referenceId, bin, targetQueue);

            BankResponse bankResponse = rabbitTemplate.convertSendAndReceiveAsType(
                    targetQueue,
                    bankRequest,
                    new ParameterizedTypeReference<BankResponse>() {
                    }
            );

            if (bankResponse == null) {
                transactionLog.setStatus("FAIL");
                transactionLog.setMessage("Bank RPC timeout");
                response = new ShaparakResponse("FAIL", referenceId, "Bank timeout");
                log.warn("Transaction {} timed out waiting for bank reply on {}", referenceId, targetQueue);
            } else {
                transactionLog.setStatus(bankResponse.status());
                transactionLog.setMessage(bankResponse.message());
                response = new ShaparakResponse(bankResponse.status(), referenceId, bankResponse.message());
            }
        } catch (Exception ex) {
            transactionLog.setStatus("FAIL");
            transactionLog.setMessage(ex.getMessage());
            response = new ShaparakResponse("FAIL", referenceId, "Bank routing error");
            log.error("Transaction {} failed routing to {}: {}", referenceId, targetQueue, ex.getMessage());
        }

        transactionLog.setCompletedAt(Instant.now());
        transactionLogRepository.save(transactionLog);

        return response;
    }

    private String maskCard(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 10) {
            return "****";
        }
        return cardNumber.substring(0, 6) + "******" + cardNumber.substring(cardNumber.length() - 4);
    }
}
