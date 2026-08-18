package com.paysim.shaparakservice.service;

import com.paysim.shaparakservice.config.BinRoutingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankRoutingService {

    private final BinRoutingProperties binRoutingProperties;

    public String resolveQueue(String cardNumber) {
        String bin = cardNumber.substring(0, 6);
        return binRoutingProperties.getBinMap().getOrDefault(bin, binRoutingProperties.getDefaultQueue());
    }
}
