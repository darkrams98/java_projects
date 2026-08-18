package com.paysim.bankservice.dto;

import java.math.BigDecimal;

public record BankRequest(String referenceId, String cardNumber, String cardName, BigDecimal amount) {
}
