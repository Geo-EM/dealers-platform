package com.dealers.payment.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtGenerator {

  public static void main(String[] args) {
    String secret = "sda-21Df.fG!cgb&asd$GDa8a7sd@FE.S16sda-21Df.fG!cgb&asd$GDa8a7sd@FE7"; // must match your JwtUtil

    String jwt = Jwts.builder()
        .setSubject("admin-user")
        .claim("roles", "GLOBAL_ADMIN") // or USER
        .claim("tenantId", "tenant5") // <-- add your tenant ID here
        .setIssuedAt(new Date())
        // .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
        .signWith(SignatureAlgorithm.HS256, secret.getBytes())
        .compact();

    System.out.println("JWT Token: " + jwt);
  }
}

// JWT:
// User:
// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJyb2xlcyI6IlVTRVIiLCJ0ZW5hbnRJZCI6InRlbmFudDUiLCJpYXQiOjE3NzM2ODIxMzR9.28VsHwBwt7EI62wpgbXvAhojTaBeNbtDrwdR3QXpM38
// Admin:
// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbi11c2VyIiwicm9sZXMiOiJHTE9CQUxfQURNSU4iLCJ0ZW5hbnRJZCI6InRlbmFudDUiLCJpYXQiOjE3NzM2ODIyMjJ9.q8hs6Pq4updjG2jR006weqtnR124_bXh9yUStX_Ct5Y