package com.server.api.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginDTO {

    @Email(message = "Debe proporcionar un correo electrónico válido")
    @NotBlank(message = "El campo correo no puede estar vacío")
    private String correo;

    @NotBlank(message = "El campo contraseña no puede estar vacío")
    private String password;

}
