package com.rba1aji.examinationmanagementsystem.utilities;

import com.rba1aji.examinationmanagementsystem.dto.JwtClaimsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthUtils {

  @Value("${jwt.secret_key}")
  private String secretKey;

  @Value("${jwt.expiration_hours}")
  private long expirationHours;

  private String encodedSecret;

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
    Map<String, Object> claimsMap = Map.of("role", claimsDto.getRole());
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .signWith(SignatureAlgorithm.ES256, secretKey)
        .setIssuedAt(new Date())
        .setExpiration(getExpirationTime())
        .addClaims(claimsMap)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public JwtClaimsDto decodeToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(token).getBody();
    JwtClaimsDto claimsDto = new JwtClaimsDto();
    claimsDto.setRole(claims.get("role").toString());
    return claimsDto;
  }

}
