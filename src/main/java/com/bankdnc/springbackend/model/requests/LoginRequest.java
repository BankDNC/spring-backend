package com.bankdnc.springbackend.model.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    @NotNull
    @NotEmpty(message = "el email no puede ser nulo")
    @Email(message = "el email no es valido")
    private String email;
    @NotNull
    @NotEmpty(message = "la contraseña no puede ser nula")
    @NotBlank(message = "la contraseña no puede estar en blanco")
    @Size(min = 8, message = "la contraseña debe tener al menos 8 caracteres")
    private String password;
}
