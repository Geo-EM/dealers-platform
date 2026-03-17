package com.dealers.inventory.tenant;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TenantFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getRequestURI();
    if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
      filterChain.doFilter(request, response);
      return;
    }

    String tenantId = request.getHeader("X-Tenant-Id");
    if (tenantId == null || tenantId.isBlank()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setContentType("application/json");
      response.getWriter().write("{\"error\": \"Missing X-Tenant-Id header\"}");
      response.flushBuffer();
      return;
    }

    try {
      TenantContext.setTenantId(tenantId);
      filterChain.doFilter(request, response);
    } finally {
      TenantContext.clear();
    }
  }
}
