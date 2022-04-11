package com.github.nadia.vacinasapi.api.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VacinaUpdateRequest {

    @Schema(description = "Atualiza nome da Vacina", example = "Maria")
    @NotBlank
    public String nome;

}
