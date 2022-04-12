package com.github.nadia.vacinasapi.api.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacinaResponse {

    @Schema(description = "Id gerado da Vacina", example = "1")
    private Long id;
    @Schema(description = "Nome da Vacina", example = "Febre Amarela")
    private String nome;
    @Schema(description = "Email do Usuário cadastrado", example = "maria@gmail.com")
    private String email;
    @Schema(description = "Data de aplicação da Vacina", example = "2022-08-13")
    private LocalDate dataAplicacao;
}
