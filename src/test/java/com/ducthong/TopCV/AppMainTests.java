package com.ducthong.TopCV;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class AppMainTests {
    @Test
    public void passwordEncode() {
        String plain = "thong";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String cipher = passwordEncoder.encode(plain);
        System.out.println(cipher);
    }

    @Test
    void contextLoads() {}
}
