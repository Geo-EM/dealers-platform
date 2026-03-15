package com.dealers.inventory.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dealers.inventory.entity.Dealer;
import com.dealers.inventory.repository.DealerRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
  private final DealerRepository dealerRepository;

  @GetMapping("dealers/countBySubscription")
  @PreAuthorize("hasRole('GLOBAL_ADMIN')")
  public ResponseEntity<Map<String, Long>> countBySubscription() {
    Map<String, Long> map = new HashMap<>();
    map.put("BASIC", dealerRepository.countBySubscriptionType(Dealer.SubscriptionType.BASIC));
    map.put("PREMIUM", dealerRepository.countBySubscriptionType(Dealer.SubscriptionType.PREMIUM));
    return ResponseEntity.ok(map);
  }
}
