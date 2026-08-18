package com.paysim.shaparakservice.controller;

import com.paysim.shaparakservice.dto.ShaparakRequest;
import com.paysim.shaparakservice.dto.ShaparakResponse;
import com.paysim.shaparakservice.service.ShaparakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shaparak")
@RequiredArgsConstructor
public class ShaparakController {

    private final ShaparakService shaparakService;

    @PostMapping("/process")
    public ShaparakResponse process(@Valid @RequestBody ShaparakRequest request) {
        return shaparakService.routeToBank(request);
    }
}
