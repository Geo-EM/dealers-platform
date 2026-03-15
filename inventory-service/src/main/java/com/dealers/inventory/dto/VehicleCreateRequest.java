package com.dealers.inventory.dto;

import com.dealers.inventory.entity.Vehicle.Status;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class VehicleCreateRequest {

  @NotNull
  private UUID dealerId; // reference only

  @NotNull
  private String model;

  @NotNull
  private BigDecimal price;

  @NotNull
  private Status status;

}