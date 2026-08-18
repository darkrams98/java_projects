package com.paysim.bankservice.listener;

import com.paysim.bankservice.dto.BankRequest;
import com.paysim.bankservice.dto.BankResponse;
import com.paysim.bankservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankTransactionListener {

    private final AccountService accountService;

    @RabbitListener(queues = "${bank.queue.name}", containerFactory = "rabbitListenerContainerFactory", concurrency = "1")
    public BankResponse handleTransaction(BankRequest request) {
        return accountService.processTransaction(request);
    }
}
