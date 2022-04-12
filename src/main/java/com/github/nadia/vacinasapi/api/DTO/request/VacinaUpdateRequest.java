package com.github.nadia.vacinasapi.api.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacinaUpdateRequest {

    @Schema(description = "Atualiza nome da Vacina", example = "Coronavac")
    @NotBlank
    public String nome;

}
