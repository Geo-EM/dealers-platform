package com.dealers.inventory.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dealers.inventory.entity.Dealer;
import com.dealers.inventory.repository.DealerRepository;
import com.dealers.inventory.tenant.TenantContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final DealerRepository dealerRepository;

  public Map<String, Long> countDealersByTenantIdAndSubscription() {

    String tenantId = TenantContext.getTenantId();

    long basic = dealerRepository.countByTenantIdAndSubscriptionType(
        tenantId,
        Dealer.SubscriptionType.BASIC);

    long premium = dealerRepository.countByTenantIdAndSubscriptionType(
        tenantId,
        Dealer.SubscriptionType.PREMIUM);

    Map<String, Long> result = new HashMap<>();

    result.put("BASIC", basic);
    result.put("PREMIUM", premium);

    return result;
  }
}