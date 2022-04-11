package com.github.nadia.vacinasapi.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

//classe para auxiliar a construção do APIExceptionHandler
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problema {

    private Integer status;
    private OffsetDateTime offsetDateTime;
    private String title;
    private List<Campo> campos;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Campo {

        private String nome;
        private String mensagem;
    }



}
