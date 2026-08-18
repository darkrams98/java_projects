package com.paysim.pspservice.document;

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
@Document(collection = "payment_logs")
public class PaymentLog {

    @Id
    private String id;
    private String referenceId;
    private String maskedCardNumber;
    private String cardName;
    private BigDecimal amount;
    private Long productId;
    private String status;
    private String shaparakReferenceId;
    private Instant createdAt;
    private Instant completedAt;
}
