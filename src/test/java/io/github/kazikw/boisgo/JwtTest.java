package io.github.kazikw.boisgo;

import io.github.kazikw.boisgo.mapper.UserMapper;
import io.github.kazikw.boisgo.mapper.UserMapperImpl;
import io.github.kazikw.boisgo.repository.UserRepository;
import io.github.kazikw.boisgo.service.AuthService;
import io.github.kazikw.boisgo.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtTest {

    @Test
    void auth(){
        JwtService service = new JwtService();
        String randomString = java.util.UUID.randomUUID().toString();
        String token = service.generateToken(randomString);
        assertTrue(service.extractUsername(token).equals(randomString));
//        assertFalse();
    }
}
