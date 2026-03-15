package com.dealers.inventory.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dealers.inventory.entity.Dealer;
import com.dealers.inventory.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

  Optional<Vehicle> findByIdAndTenantId(UUID id, String tenantId);

  List<Vehicle> findAllByTenantId(String tenantId);

  @Query("SELECT v FROM Vehicle v WHERE v.tenantId = :tenantId AND v.dealer.subscriptionType = :subscription")
  List<Vehicle> findAllByTenantIdAndDealerSubscription(@Param("tenantId") String tenantId,
      @Param("subscription") Dealer.SubscriptionType subscription);

}
