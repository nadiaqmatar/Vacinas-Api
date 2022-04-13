package com.github.nadia.vacinasapi.api.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @Schema(description = "Nome do Usuário", example = "Maria")
    @NotBlank
    private String nome;
    @Schema(description = "CPF do Usuário", example = "103.966.960-31")
    @CPF
    private String cpf;
    @Schema(description = "Email do Usuário", example = "maria@gmail.com")
    @Email
    private String email;
    @Schema(description = "Data de Nascimento do Usuário", example = "1996-09-17")
    @NotNull
    private LocalDate dataNascimento;
}
