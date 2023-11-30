package shop.jnjeaaaat.easyrsv.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    void passwordEncoderTest() {
        //given
        String password = "testpassword";

        //when
        String encryptedPassword = passwordEncoder.encode(password);

        //then
        assertTrue(passwordEncoder.matches(password, encryptedPassword));
    }

    @Test
    @Transactional
    void userPasswordMatch() {
        //given
        userRepository.save(User.builder()
                        .email("email")
                        .password("password")
                        .name("name")
                        .build()
        );

        User user = userRepository.getByEmail("email");

        // then
        assertTrue(passwordEncoder.matches("password", user.getPassword()));
    }
}
