package com.paysim.shaparakservice.repository;

import com.paysim.shaparakservice.document.TransactionLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionLogRepository extends MongoRepository<TransactionLog, String> {
}
