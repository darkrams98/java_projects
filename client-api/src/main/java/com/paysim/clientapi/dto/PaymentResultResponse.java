package com.paysim.clientapi.dto;

public record PaymentResultResponse(String status, String referenceId, String message) {
}
