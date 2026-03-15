package com.dealers.inventory.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dealers.inventory.entity.Vehicle;
import com.dealers.inventory.repository.VehicleRepository;
import com.dealers.inventory.repository.VehicleSpecification;
import com.dealers.inventory.tenant.TenantContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {
  private final VehicleRepository vehicleRepository;

  public Vehicle createVehicle(Vehicle vehicle) {
    vehicle.setTenantId(TenantContext.getTenantId());
    return vehicleRepository.save(vehicle);
  }

  public Vehicle getVehicle(UUID id) {
    return vehicleRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
        .orElseThrow(() -> new RuntimeException("Vehicle not found or cross-tenant access blocked"));
  }

  // public List<Vehicle> getVehicles(String subscription) {
  // if (subscription != null) {
  // return
  // vehicleRepository.findAllByTenantIdAndDealerSubscription(TenantContext.getTenantId(),
  // SubscriptionType.valueOf(subscription));
  // }
  // return vehicleRepository.findAllByTenantId(TenantContext.getTenantId());
  // }

  public Page<Vehicle> getVehicles(String tenantId,
      String model,
      Vehicle.Status status,
      BigDecimal priceMin,
      BigDecimal priceMax,
      String subscription,
      Pageable pageable) {

    Specification<Vehicle> spec = Specification
        .where(VehicleSpecification.hasTenantId(tenantId))
        .and(VehicleSpecification.hasModel(model))
        .and(VehicleSpecification.hasStatus(status))
        .and(VehicleSpecification.hasPriceBetween(priceMin, priceMax))
        .and(VehicleSpecification.dealerHasSubscription(subscription));

    return vehicleRepository.findAll(spec, pageable);
  }

  public void deleteVehicle(UUID id) {
    vehicleRepository.deleteById(id);
  }
}
