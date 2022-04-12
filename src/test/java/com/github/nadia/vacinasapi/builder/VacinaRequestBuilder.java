package com.github.nadia.vacinasapi.builder;

import com.github.nadia.vacinasapi.api.DTO.request.VacinaRequest;
import lombok.Builder;

@Builder
public class VacinaRequestBuilder {

    @Builder.Default
    private String nome = "Coronavac";
    @Builder.Default
    private String email = "maria@teste.com";

    public VacinaRequest toVacinaRequest(){
        return new VacinaRequest(nome,email);
    }
}
