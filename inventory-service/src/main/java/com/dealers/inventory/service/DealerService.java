package com.dealers.inventory.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dealers.inventory.entity.Dealer;
import com.dealers.inventory.repository.DealerRepository;
import com.dealers.inventory.tenant.TenantContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DealerService {

  private final DealerRepository dealerRepository;

  public Dealer createDealer(Dealer dealer) {
    dealer.setTenantId(TenantContext.getTenantId());
    return dealerRepository.save(dealer);

  }

  public Dealer getDealer(UUID id) {
    return dealerRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
        .orElseThrow(() -> new RuntimeException("Dealer not found or cross-tenant access blocked"));
  }

  public List<Dealer> getAllDealer() {
    return dealerRepository.findAllByTenantId(TenantContext.getTenantId());
  }
}
