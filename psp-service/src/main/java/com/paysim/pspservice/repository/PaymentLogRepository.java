package com.paysim.pspservice.repository;

import com.paysim.pspservice.document.PaymentLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentLogRepository extends MongoRepository<PaymentLog, String> {
}
