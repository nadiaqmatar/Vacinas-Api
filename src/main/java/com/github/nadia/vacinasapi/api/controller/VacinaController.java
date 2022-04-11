package com.github.nadia.vacinasapi.api.controller;

import com.github.nadia.vacinasapi.api.DTO.request.VacinaRequest;
import com.github.nadia.vacinasapi.api.DTO.request.VacinaUpdateRequest;
import com.github.nadia.vacinasapi.api.DTO.response.VacinaResponse;
import com.github.nadia.vacinasapi.core.mapper.VacinaMapper;
import com.github.nadia.vacinasapi.domain.service.VacinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Vacinas Controller")
@RestController
@RequestMapping("api/v1/vacinas")
public class VacinaController {

    @Autowired
    private VacinaService vacinaService;

    @Autowired
    private VacinaMapper vacinaMapper;

    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @GetMapping
    @Operation(summary = "Busca todas Vacinas")
    public List<VacinaResponse> buscarTodos(){
        return vacinaMapper.toVacinaResponseList(vacinaService.buscarTodos());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Vacina não encontrada.")
    })
    @GetMapping("/{id}")
    @Operation(summary = "Busca Vacina por Id")
    public VacinaResponse buscarPorId(@PathVariable("id") Long id){
        return vacinaMapper.toVacinaResponse(vacinaService.buscarPorId(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vacina cadastrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Vacina não encontrada.")
    })
    @PostMapping
    @Operation(summary = "Salva Vacinas")
    @ResponseStatus(HttpStatus.CREATED)
    public VacinaResponse salvar(@Valid @RequestBody VacinaRequest vacinaRequest){
        var vacina = vacinaMapper.toVacinaEntity(vacinaRequest);
        return vacinaMapper.toVacinaResponse(vacinaService.salvar(vacina));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacina atualizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados Vacina")
    public VacinaResponse atualizar(@PathVariable("id") Long id, @Valid @RequestBody VacinaUpdateRequest vacinaRequest){
        var vacina = vacinaMapper.toVacinaEntity(vacinaRequest);
        return vacinaMapper.toVacinaResponse(vacinaService.atualizar(id, vacina));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vacina deletada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Id de Vacina inválido.")
    })
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta vacinas por Id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id){
        vacinaService.deletar(id);
    }
}
