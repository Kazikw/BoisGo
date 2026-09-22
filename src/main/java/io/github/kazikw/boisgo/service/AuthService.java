package io.github.kazikw.boisgo.service;

import io.github.kazikw.boisgo.dto.request.RegisterRequest;
import io.github.kazikw.boisgo.dto.response.UserResponse;
import io.github.kazikw.boisgo.entity.Role;
import io.github.kazikw.boisgo.entity.User;
import io.github.kazikw.boisgo.exception.UserAlreadyExistsException;
import io.github.kazikw.boisgo.mapper.UserMapper;
import io.github.kazikw.boisgo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    @Transactional
    public UserResponse registerUser(RegisterRequest request){
        String login = request.login();
        if(userRepository.existsByLogin(login)){
           throw new UserAlreadyExistsException("Login jest juz zajety!");
        }
        if(userRepository.existsByEmail(request.email())){
            throw new UserAlreadyExistsException("Email jest juz zajety!");
        }
        if(userRepository.existsByNick(request.nick())){
            throw new UserAlreadyExistsException("Nick jest juz zajety!");
        }

        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setEmail(request.email());
        newUser.setNick(request.nick());
        newUser.setRole(Role.USER);
        User savedUser = userRepository.save(newUser);
        return userMapper.entityToResponse(savedUser);
    }

}
