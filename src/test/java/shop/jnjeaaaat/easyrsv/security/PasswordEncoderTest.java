package shop.jnjeaaaat.easyrsv.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoderTest() {
        //given
        String password = "testpassword";

        //when
        String encryptedPassword = passwordEncoder.encode(password);

        //then
        assertTrue(passwordEncoder.matches(password, encryptedPassword));
    }
}
