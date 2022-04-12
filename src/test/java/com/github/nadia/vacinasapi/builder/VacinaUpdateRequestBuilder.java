package com.github.nadia.vacinasapi.builder;

import com.github.nadia.vacinasapi.api.DTO.request.VacinaUpdateRequest;
import lombok.Builder;

@Builder
public class VacinaUpdateRequestBuilder {

    @Builder.Default
    public String nome = "Pfizer";

    public VacinaUpdateRequest toVacinaUpdateRequest(){
        return new VacinaUpdateRequest(nome);
    }

}
