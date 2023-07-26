package com.rba1aji.examinationmanagementsystem.utilities;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {
  public static String encrypt(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public static boolean verify(String password, String hashed) {
    return BCrypt.checkpw(password, hashed);
  }
}
