package com.dealers.payment.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dealers.payment.entity.PaymentTransaction;
import com.dealers.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/initiate")
  public ResponseEntity<PaymentTransaction> initiatePayment(@RequestBody PaymentTransaction tx,
      @RequestHeader("Idempotency-Key") String key) {

    return ResponseEntity.ok(paymentService.initiate(tx, key));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentTransaction> getStatus(@PathVariable UUID id) {
    return ResponseEntity.ok(paymentService.get(id));
  }
}
