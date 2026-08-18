package com.paysim.bankservice.service;

import com.paysim.bankservice.constant.TransactionStatus;
import com.paysim.bankservice.dto.BankRequest;
import com.paysim.bankservice.dto.BankResponse;
import com.paysim.bankservice.entity.Account;
import com.paysim.bankservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public BankResponse processTransaction(BankRequest request) {
        Optional<Account> accountOpt = accountRepository.findByCardNumber(request.cardNumber());

        if (accountOpt.isEmpty()) {
            log.warn("Transaction {} rejected: account not found", request.referenceId());
            return new BankResponse(TransactionStatus.FAIL.name(), request.referenceId(), "Account not found");
        }

        Account account = accountOpt.get();

        if (account.getBalance().compareTo(request.amount()) < 0) {
            log.warn("Transaction {} rejected: insufficient funds", request.referenceId());
            return new BankResponse(TransactionStatus.INSUFFICIENT_FUNDS.name(), request.referenceId(), "Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(request.amount()));
        accountRepository.save(account);

        log.info("Transaction {} completed successfully", request.referenceId());
        return new BankResponse(TransactionStatus.SUCCESS.name(), request.referenceId(), "Transaction completed");
    }
}
