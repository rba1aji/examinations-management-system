package com.rba1aji.examinationmanagementsystem.utilities;

import com.rba1aji.examinationmanagementsystem.dto.JwtClaimsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthUtils {

  @Value("${jwt.secret_key}")
  private String secretKey;

  @Value("${jwt.expiration_hours}")
  private long expirationHours;

  private String encodedSecret;

  private final Logger logger = LoggerFactory.getLogger(JwtAuthUtils.class);

  @PostConstruct
  protected void init() {
    this.encodedSecret = generateEncodedSecret(this.secretKey);
  }

  private String generateEncodedSecret(String plainSecret) {
    return Base64.getEncoder().encodeToString(plainSecret.getBytes());
  }

  private Date getExpirationTime() {
    Date now = new Date();
    long expireInMilis = TimeUnit.HOURS.toMillis(expirationHours);
    return new Date(expireInMilis + now.getTime());
  }

  public String generateToken(JwtClaimsDto claimsDto) {
    Map<String, Object> claimsMap = new HashMap<>();
    claimsMap.put("role", claimsDto.getRole());
    claimsMap.put("userId", claimsDto.getUserId());
    claimsMap.put("username", claimsDto.getUsername());
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .setClaims(claimsMap)
        .setIssuedAt(new Date(now))
        .setExpiration(getExpirationTime())
        .signWith(SignatureAlgorithm.HS256, encodedSecret)
        .compact();
  }

  public boolean isExpired(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return false;
    } catch (Exception e) {
      return true;
    }
  }

  public JwtClaimsDto decodeToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(token).getBody();
    JwtClaimsDto claimsDto = JwtClaimsDto.builder()
        .role((String) claims.get("role"))
        .userId((Integer) claims.get("userId"))
        .username((String) claims.get("username"))
        .build();
    return claimsDto;
  }

}
