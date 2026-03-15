package com.dealers.inventory.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealers.inventory.entity.Dealer;

public interface DealerRepository extends JpaRepository<Dealer, UUID> {

  Optional<Dealer> findByIdAndTenantId(UUID id, String tenantId);

  List<Dealer> findAllByTenantId(String tenantId);

  long countBySubscriptionType(Dealer.SubscriptionType type);
}