package com.github.nadia.vacinasapi.api.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UsuarioResponse {

    @Schema(description = "Id gerado do Usuário", example = "1")
    private Long id;
    @Schema(description = "Nome do Usuário", example = "Maria")
    private String nome;
    @Schema(description = "CPF do Usuário", example = "103.966.960-31")
    private String cpf;
    @Schema(description = "Email do Usuário", example = "maria@gmail.com")
    private String email;
    @Schema(description = "Data de Nascimento do Usuário", example = "1996-09-17")
    private LocalDate dataNascimento;
}
