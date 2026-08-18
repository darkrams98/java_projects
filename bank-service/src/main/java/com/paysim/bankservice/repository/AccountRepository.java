package com.paysim.bankservice.repository;

import com.paysim.bankservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCardNumber(String cardNumber);
}
