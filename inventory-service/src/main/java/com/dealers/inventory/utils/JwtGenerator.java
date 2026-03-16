package com.dealers.inventory.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtGenerator {

  public static void main(String[] args) {
    String secret = "sda-21Df.fG!cgb&asd$GDa8a7sd@FE.S16sda-21Df.fG!cgb&asd$GDa8a7sd@FE7"; // must match your JwtUtil

    String jwt = Jwts.builder()
        .setSubject("test-user")
        .claim("roles", "USER") // or GLOBAL_ADMIN
        .claim("tenantId", "tenant5") // <-- add your tenant ID here
        .setIssuedAt(new Date())
        // .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
        .signWith(SignatureAlgorithm.HS256, secret.getBytes())
        .compact();

    System.out.println("JWT Token: " + jwt);
  }
}
// JWT:
// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJyb2xlcyI6IlVTRVIiLCJ0ZW5hbnRJZCI6InRlbmFudDUiLCJpYXQiOjE3NzM2NzUzNjZ9.dzl9rTy8K2k0EkANChLwwcEvgjJvFkg8QIdueloWK14