package com.github.nadia.vacinasapi.domain.service;

import com.github.nadia.vacinasapi.domain.entity.Vacina;
import com.github.nadia.vacinasapi.domain.exception.NotFoundException;
import com.github.nadia.vacinasapi.domain.exception.ServiceException;
import com.github.nadia.vacinasapi.domain.repository.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VacinaService {

    @Autowired
    private VacinaRepository vacinaRepository;

    public List<Vacina> buscarTodos(){
        return vacinaRepository.findAll();
    }

    public Vacina salvar(Vacina vacina){
        vacina.setDataAplicacao(LocalDate.now());
        return vacinaRepository.save(vacina);
    }

    public Vacina buscarPorId(Long id){
       return vacinaRepository.findById(id).orElseThrow(()-> new NotFoundException("Vacina não encontrada."));
    }

    public Vacina atualizar(Long id, Vacina vacina){
        var vacinaNoBanco = check(id);
        vacina.setId(vacinaNoBanco.getId());
        vacina.setUsuario(vacinaNoBanco.getUsuario());
        vacina.setDataAplicacao(vacinaNoBanco.getDataAplicacao());
        return vacinaRepository.save(vacina);
    }

    public void deletar(Long id){
        check(id);
        vacinaRepository.deleteById(id);
    }

    private Vacina check(Long id){
        return vacinaRepository.findById(id).orElseThrow(()-> new ServiceException("Vacina não encontrada."));
    }
}
