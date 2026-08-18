package com.paysim.clientapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record CardPaymentRequest(
        @NotBlank @Pattern(regexp = "^\\d{12,19}$") String cardNumber,
        @NotBlank String cardName,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount,
        Long productId
) {
}
