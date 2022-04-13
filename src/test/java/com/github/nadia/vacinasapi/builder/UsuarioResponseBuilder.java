package com.github.nadia.vacinasapi.builder;

import com.github.nadia.vacinasapi.api.DTO.response.UsuarioResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UsuarioResponseBuilder {

    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String nome = "Maria";
    @Builder.Default
    private String cpf = "107.523.366-65";
    @Builder.Default
    private String email = "maria@teste.com";
    @Builder.Default
    private LocalDate dataNascimento = LocalDate.parse("1994-05-05");

    public UsuarioResponse toUsuarioResponse(){
        return new UsuarioResponse(id,nome,cpf,email,dataNascimento);
    }

}
