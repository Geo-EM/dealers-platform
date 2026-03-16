package com.dealers.payment.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(nullable = false)
  private String tenantId;

  @Column(nullable = false)
  private UUID dealerId;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Method method;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  @Column(nullable = false, unique = true)
  private String requestId;

  private Instant createdAt;

  private Instant updatedAt;

  public enum Method {
    UPI,
    CARD,
    NET_BANKING
  }

  public enum Status {
    PENDING,
    SUCCESS,
    FAILED
  }
}