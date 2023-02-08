package com.bankdnc.springbackend.model.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    @NotNull
    @NotEmpty(message = "el nombre no puede ser nulo")
    @NotBlank(message = "el nombre no puede estar en blanco")
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String name;
    @NotNull
    @NotEmpty(message = "el apellido no puede ser nulo")
    @NotBlank(message = "el apellido no puede estar en blanco")
    @Schema(description = "Apellido del usuario", example = "Perez")
    private String lastName;
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
    @NotNull
    @NotEmpty(message = "el dni no puede ser nulo")
    @NotBlank(message = "el dni no puede estar en blanco")
    @Schema(description = "DNI del usuario", example = "12345678")
    private String dni;
    @NotNull
    @NotEmpty(message = "el telefono no puede ser nulo")
    @NotBlank(message = "el telefono no puede estar en blanco")
    @Schema(description = "Telefono del usuario", example = "12345678")
    private String phone;
}
