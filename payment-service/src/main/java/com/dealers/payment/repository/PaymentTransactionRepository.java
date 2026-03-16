package com.dealers.payment.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealers.payment.entity.PaymentTransaction;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID> {

  Optional<PaymentTransaction> findByRequestId(String requestId);

  Optional<PaymentTransaction> findByIdAndTenantId(UUID id, String tenantId);
}
