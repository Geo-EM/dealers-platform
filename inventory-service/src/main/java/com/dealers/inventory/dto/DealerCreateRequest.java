package com.dealers.inventory.dto;

import com.dealers.inventory.entity.Dealer.SubscriptionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DealerCreateRequest {

  @NotBlank
  private String name;

  @Email
  @NotBlank
  private String email;

  @NotNull
  private SubscriptionType subscriptionType;
}