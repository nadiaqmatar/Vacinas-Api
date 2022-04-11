package com.github.nadia.vacinasapi.api.controller;

import com.github.nadia.vacinasapi.api.DTO.request.UsuarioRequest;
import com.github.nadia.vacinasapi.api.DTO.request.UsuarioUpdateRequest;
import com.github.nadia.vacinasapi.api.DTO.response.UsuarioResponse;
import com.github.nadia.vacinasapi.api.DTO.response.VacinaResponse;
import com.github.nadia.vacinasapi.core.mapper.UsuarioMapper;
import com.github.nadia.vacinasapi.core.mapper.VacinaMapper;
import com.github.nadia.vacinasapi.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Usuários Controller")
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private VacinaMapper vacinaMapper;

    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @GetMapping
    @Operation(summary = "Busca todos os Usuários")
    public List<UsuarioResponse> buscarTodos(){
        return usuarioMapper.toUsuarioResponseList(usuarioService.buscarTodos());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @GetMapping("/{id}")
    @Operation(summary = "Busca Usuário por Id")
    public UsuarioResponse buscarPorId(@PathVariable("id") Long id){
       return usuarioMapper.toUsuarioResponse(usuarioService.buscarPorId(id));

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados não foram preenchidos corretamente.")
    })
    @PostMapping
    @Operation(summary = "Salva Usuários")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse salvar(@Valid @RequestBody UsuarioRequest request) {
        var usuario = usuarioMapper.toUsuarioEntity(request);
        return usuarioMapper.toUsuarioResponse(usuarioService.salvar(usuario));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados do Usuário")
    public UsuarioResponse atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioUpdateRequest request){
        var usuario = usuarioMapper.toUsuarioEntity(request);
        System.out.println(usuario);
        return usuarioMapper.toUsuarioResponse(usuarioService.atualizar(id, usuario));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @GetMapping("/vacinas/{id}")
    @Operation(summary = "Lista de vacinas do Usuário")
    public List<VacinaResponse> listarVacinas(@PathVariable("id") Long id){
        return vacinaMapper.toVacinaResponseList(usuarioService.listarVacinas(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Id de Usuário inválido.")
    })
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta Usuário por Id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id){
        usuarioService.deletar(id);
    }
}
