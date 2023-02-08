package com.bankdnc.springbackend.model.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    @NotNull
    @NotEmpty(message = "el email no puede ser nulo")
    @Email(message = "el email no es valido")
    @Schema(description = "Email del usuario", example = "juanperez@gmail.com")
    private String email;
    @NotNull
    @NotEmpty(message = "la contraseña no puede ser nula")
    @NotBlank(message = "la contraseña no puede estar en blanco")
    @Size(min = 8, message = "la contraseña debe tener al menos 8 caracteres")
    @Schema(description = "Contraseña del usuario", example = "12345678")
    private String password;
}
