package com.github.nadia.vacinasapi.core.mapper;

import com.github.nadia.vacinasapi.api.DTO.request.VacinaRequest;
import com.github.nadia.vacinasapi.api.DTO.request.VacinaUpdateRequest;
import com.github.nadia.vacinasapi.api.DTO.response.VacinaResponse;
import com.github.nadia.vacinasapi.domain.entity.Usuario;
import com.github.nadia.vacinasapi.domain.entity.Vacina;
import com.github.nadia.vacinasapi.domain.exception.ServiceException;
import com.github.nadia.vacinasapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacinaMapper {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Vacina toVacinaEntity(VacinaRequest request) {
        var usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow(()-> new ServiceException("Email inválido. Nenhum usuário resgistrado com esse email."));
        Vacina vacina = new Vacina();
        vacina.setNome(request.getNome());
        vacina.setUsuario(usuario);
        return vacina;
    }

    public Vacina toVacinaEntity(VacinaUpdateRequest request) {
        Vacina vacina = new Vacina();
        vacina.setNome(request.getNome());
        return vacina;
    }

    public VacinaResponse toVacinaResponse(Vacina vacina) {
        VacinaResponse response = new VacinaResponse();
        response.setId(vacina.getId());
        response.setNome(vacina.getNome());
        response.setEmail(vacina.getUsuario().getEmail());
        response.setDataAplicacao(vacina.getDataAplicacao());
        return response;
    }

    public List<VacinaResponse> toVacinaResponseList(List<Vacina> vacinas) {
        List<VacinaResponse> responseList = new ArrayList<>();
        for (Vacina vacina : vacinas) {
            VacinaResponse vacinaResponse = this.toVacinaResponse(vacina);
            responseList.add(vacinaResponse);
        }
        return responseList;
    }

}