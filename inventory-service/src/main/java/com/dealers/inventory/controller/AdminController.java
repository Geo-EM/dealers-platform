package com.dealers.inventory.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dealers.inventory.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/dealers/countBySubscription")
  @PreAuthorize("hasRole('GLOBAL_ADMIN')")
  public Map<String, Long> countDealersBySubscription() {
    return adminService.countDealersByTenantIdAndSubscription();
  }
}