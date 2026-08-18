package com.paysim.shaparakservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shaparak_transactions")
public class TransactionLog {

    @Id
    private String id;
    private String pspReferenceId;
    private String referenceId;
    private String bin;
    private String targetQueue;
    private String maskedCardNumber;
    private BigDecimal amount;
    private String status;
    private String message;
    private Instant createdAt;
    private Instant completedAt;
}
