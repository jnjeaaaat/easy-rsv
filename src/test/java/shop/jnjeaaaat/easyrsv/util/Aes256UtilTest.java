package shop.jnjeaaaat.easyrsv.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import shop.jnjeaaaat.easyrsv.utils.Aes256Util;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Aes256UtilTest {

    @Test
    void encrypt() {
        String encrypt = Aes256Util.encrypt("Hello world");
        assertEquals("Hello world", Aes256Util.decrypt(encrypt));
    }
}
