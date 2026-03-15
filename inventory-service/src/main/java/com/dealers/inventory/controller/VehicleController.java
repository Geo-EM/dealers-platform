package com.dealers.inventory.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dealers.inventory.entity.Vehicle;
import com.dealers.inventory.service.VehicleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("vehicles")
@RequiredArgsConstructor
public class VehicleController {

  private final VehicleService vehicleService;

  @PostMapping
  public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
    Vehicle saved = vehicleService.createVehicle(vehicle);
    return new ResponseEntity<>(saved, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Vehicle> getVehicle(@PathVariable UUID id) {
    Vehicle vehicle = vehicleService.getVehicle(id);
    return ResponseEntity.ok(vehicle);
  }

  @GetMapping
  public ResponseEntity<List<Vehicle>> getVehicles(
      @RequestParam(required = false) String model,
      @RequestParam(required = false) Vehicle.Status status,
      @RequestParam(required = false) Double priceMin,
      @RequestParam(required = false) Double priceMax,
      @RequestParam(required = false) String subscription) {
    List<Vehicle> vehicles = vehicleService.getVehicles(subscription);
    return ResponseEntity.ok(vehicles);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Vehicle> updateVehicle(@PathVariable UUID id, @RequestBody Vehicle vehicleData) {
    Vehicle vehicle = vehicleService.getVehicle(id);

    String model = vehicleData.getModel() != null ? vehicleData.getModel() : vehicle.getModel();
    BigDecimal price = vehicleData.getPrice() != null ? vehicleData.getPrice() : vehicle.getPrice();
    Vehicle.Status status = vehicleData.getStatus() != null ? vehicle.getStatus()
        : vehicle.getStatus();

    vehicle.setModel(model);
    vehicle.setPrice(price);
    vehicle.setStatus(status);

    return ResponseEntity.ok(vehicleService.createVehicle(vehicle));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVehicle(@PathVariable UUID id) {
    Vehicle vehicle = vehicleService.getVehicle(id);
    vehicleService.deleteVehicle(vehicle.getId());
    return ResponseEntity.noContent().build();
  }
}
