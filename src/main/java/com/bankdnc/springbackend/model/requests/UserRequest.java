package com.bankdnc.springbackend.model.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    @NotNull
    @NotEmpty(message = "el nombre no puede ser nulo")
    @NotBlank(message = "el nombre no puede estar en blanco")
    private String name;
    @NotNull
    @NotEmpty(message = "el apellido no puede ser nulo")
    @NotBlank(message = "el apellido no puede estar en blanco")
    private String lastName;
    @NotNull
    @NotEmpty(message = "el email no puede ser nulo")
    @Email(message = "el email no es valido")
    private String email;
    @NotNull
    @NotEmpty(message = "la contraseña no puede ser nula")
    @NotBlank(message = "la contraseña no puede estar en blanco")
    @Size(min = 8, message = "la contraseña debe tener al menos 8 caracteres")
    private String password;
    @NotNull
    @NotEmpty(message = "el dni no puede ser nulo")
    @NotBlank(message = "el dni no puede estar en blanco")
    private String dni;
    @NotNull
    @NotEmpty(message = "el telefono no puede ser nulo")
    @NotBlank(message = "el telefono no puede estar en blanco")
    private String phone;
}
