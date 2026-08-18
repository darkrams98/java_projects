package com.paysim.pspservice.dto;

import java.math.BigDecimal;

public record ShaparakRequest(String pspReferenceId, String cardNumber, String cardName, BigDecimal amount) {
}
