package com.dealers.payment.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dealers.payment.entity.PaymentTransaction;
import com.dealers.payment.repository.PaymentTransactionRepository;
import com.dealers.payment.tenant.TenantContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentTransactionRepository paymentTransactionRepository;

  public PaymentTransaction initiate(PaymentTransaction paymentTransaction, String idempotencyKey) {

    var existingTransaction = paymentTransactionRepository.findByRequestId(idempotencyKey);

    if (existingTransaction.isPresent()) {
      return existingTransaction.get();
    }

    // paymentTransaction.setId(UUID.randomUUID());
    paymentTransaction.setTenantId(TenantContext.getTenantId());
    paymentTransaction.setRequestId(idempotencyKey);
    paymentTransaction.setStatus(PaymentTransaction.Status.PENDING);
    Instant instantNow = Instant.now();
    paymentTransaction.setCreatedAt(instantNow);
    paymentTransaction.setUpdatedAt(instantNow);

    PaymentTransaction savedTransaction = paymentTransactionRepository.save(paymentTransaction);

    completeAsync(savedTransaction.getId());

    return savedTransaction;
  }

  public PaymentTransaction get(UUID id) {

    return paymentTransactionRepository
        .findByIdAndTenantId(id, TenantContext.getTenantId())
        .orElseThrow(() -> new RuntimeException("Transaction not found"));
  }

  @Async
  public void completeAsync(UUID id) {

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    PaymentTransaction paymentTransaction = paymentTransactionRepository.findById(id).orElseThrow();

    paymentTransaction.setStatus(PaymentTransaction.Status.SUCCESS);
    paymentTransaction.setUpdatedAt(Instant.now());

    paymentTransactionRepository.save(paymentTransaction);
  }

}
