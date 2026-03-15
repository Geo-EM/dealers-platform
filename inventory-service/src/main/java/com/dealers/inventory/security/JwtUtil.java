package com.dealers.inventory.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  private final SecretKey secretKey = Keys.hmacShaKeyFor("sda-21Df.fG!cgb&asd$GDa8a7sd@FE.S16sda-21Df.fG!cgb&asd$GDa8a7sd@FE7".getBytes());

  public Claims extractClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
  }
}
