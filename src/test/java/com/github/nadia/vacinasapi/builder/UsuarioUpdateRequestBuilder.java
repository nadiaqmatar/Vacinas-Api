package com.github.nadia.vacinasapi.builder;

import com.github.nadia.vacinasapi.api.DTO.request.UsuarioUpdateRequest;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UsuarioUpdateRequestBuilder {

    @Builder.Default
    private String nome = "João";
    @Builder.Default
    private String cpf = "107.523.366-65";
    @Builder.Default
    private String email = "joao@teste.com";
    @Builder.Default
    private LocalDate dataNascimento = LocalDate.parse("1995-09-17");

    public UsuarioUpdateRequest toUsuarioUpdateRequest(){
        return new UsuarioUpdateRequest(nome,cpf,email,dataNascimento);
    }
}
