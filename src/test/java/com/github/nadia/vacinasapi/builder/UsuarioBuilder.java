package com.github.nadia.vacinasapi.builder;

import com.github.nadia.vacinasapi.domain.entity.Usuario;
import com.github.nadia.vacinasapi.domain.entity.Vacina;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
public class UsuarioBuilder {

    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String nome = "Maria";
    @Builder.Default
    private String cpf = "111.111.111-11";
    @Builder.Default
    private String email = "maria@teste.com";
    @Builder.Default
    private LocalDate dataNascimento = LocalDate.parse("1994-05-05");
    @Builder.Default
    private List<Vacina> vacinas = new ArrayList<>();

    public Usuario toUsuario(){
        return new Usuario(id,nome,cpf,email,dataNascimento,vacinas);
    }
}
