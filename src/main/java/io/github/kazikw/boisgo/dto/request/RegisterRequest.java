package io.github.kazikw.boisgo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Size(min = 6, message = "Login musi mieć minimum 6 znaków")
        @NotBlank(message = "Login jest wymagany")
        String login,
        @NotBlank(message = "Haslo jest wymagane")
        @Size(min = 7, message = "Hasło musi mieć minimum 7 znaków")
        String password,
        @NotBlank(message = "Nick jest wymagany")
        @Size(min = 6, message = "Nick musi mieć minimum 6 znaków")
        String nick,
        @NotBlank(message = "email jest wymagany")
        @Email(message = "Podaj poprawny adres email!")
        String email
) {

}
