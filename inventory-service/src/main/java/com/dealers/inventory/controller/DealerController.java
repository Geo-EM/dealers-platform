package com.dealers.inventory.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dealers.inventory.entity.Dealer;
import com.dealers.inventory.entity.Dealer.SubscriptionType;
import com.dealers.inventory.service.DealerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dealers")
@RequiredArgsConstructor
public class DealerController {
  private final DealerService dealerService;

  @PostMapping
  public ResponseEntity<Dealer> creteDealer(@RequestBody Dealer dealer) {
    Dealer saved = dealerService.createDealer(dealer);
    return new ResponseEntity<>(saved, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Dealer> getDealer(@PathVariable UUID id) {
    Dealer dealer = dealerService.getDealer(id);
    return ResponseEntity.ok(dealer);
  }

  @GetMapping
  public ResponseEntity<List<Dealer>> getAllDealers() {
    List<Dealer> dealers = dealerService.getAllDealer();
    return ResponseEntity.ok(dealers);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Dealer> updateDealer(@PathVariable UUID id, @RequestBody Dealer dealerData) {
    Dealer dealer = dealerService.getDealer(id);

    String name = dealerData.getName() != null ? dealerData.getName() : dealer.getName();
    String email = dealerData.getEmail() != null ? dealerData.getEmail() : dealer.getEmail();
    SubscriptionType subscriptionType = dealerData.getSubscriptionType() != null ? dealerData.getSubscriptionType()
        : dealer.getSubscriptionType();

    dealer.setName(name);
    dealer.setEmail(email);
    dealer.setSubscriptionType(subscriptionType);

    return ResponseEntity.ok(dealerService.createDealer(dealer));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDealer(@PathVariable UUID id) {
    Dealer dealer = dealerService.getDealer(id);
    dealerService.deleteDealer(dealer.getId());
    return ResponseEntity.noContent().build();
  }
}
