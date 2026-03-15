package com.dealers.inventory.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.dealers.inventory.entity.Vehicle;
import com.dealers.inventory.entity.Vehicle.Status;

public class VehicleSpecification {

  public static Specification<Vehicle> hasModel(String model) {
    return (root, query, cb) -> model == null ? null : cb.equal(root.get("model"), model);
  }

  public static Specification<Vehicle> hasStatus(Status status) {
    return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
  }

  public static Specification<Vehicle> hasPriceBetween(BigDecimal min, BigDecimal max) {
    return (root, query, cb) -> {
      if (min == null && max == null)
        return null;
      if (min != null && max != null)
        return cb.between(root.get("price"), min, max);
      if (min != null)
        return cb.greaterThanOrEqualTo(root.get("price"), min);
      return cb.lessThanOrEqualTo(root.get("price"), max);
    };
  }

  public static Specification<Vehicle> hasTenantId(String tenantId) {
    return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
  }

  public static Specification<Vehicle> dealerHasSubscription(String subscriptionType) {
    return (root, query, cb) -> {
      if (subscriptionType == null)
        return null;
      query.distinct(true);
      return cb.equal(root.join("dealer").get("subscriptionType"), subscriptionType);
    };
  }
}