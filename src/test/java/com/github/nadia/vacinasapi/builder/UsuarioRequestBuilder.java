package com.github.nadia.vacinasapi.builder;

import com.github.nadia.vacinasapi.api.DTO.request.UsuarioRequest;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UsuarioRequestBuilder {

    @Builder.Default
    private String nome = "Maria";
    @Builder.Default
    private String cpf = "107.523.366-65";
    @Builder.Default
    private String email = "maria@teste.com";
    @Builder.Default
    private LocalDate dataNascimento = LocalDate.parse("1994-05-05");

    public UsuarioRequest toUsuarioRequest(){
        return new UsuarioRequest(nome,cpf,email,dataNascimento);
    }
}
