package io.github.kazikw.boisgo.dto.response;

import io.github.kazikw.boisgo.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserResponse(
        String nick,
        String email,
        Role role
) {
}
