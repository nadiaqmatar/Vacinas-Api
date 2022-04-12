package com.github.nadia.vacinasapi.api.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacinaRequest {

    @Schema(description = "Nome da Vacina", example = "Febre Amarela")
    @NotBlank
    private String nome;
    @Schema(description = "Email do Usuário cadastrado", example = "maria@gmail.com")
    @NotBlank
    @Email
    private String email;
}
