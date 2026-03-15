package com.dealers.inventory.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtGenerator {

  public static void main(String[] args) {
    String secret = "sda-21Df.fG!cgb&asd$GDa8a7sd@FE.S16sda-21Df.fG!cgb&asd$GDa8a7sd@FE7"; // must match your JwtUtil secret
    String jwt = Jwts.builder()
        .setSubject("test-user")
        .claim("roles", "USER") // or GLOBAL_ADMIN
        .setIssuedAt(new Date())
        // .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
        .signWith(SignatureAlgorithm.HS256, secret.getBytes())
        .compact();

    System.out.println("JWT Token: " + jwt);
  }
}