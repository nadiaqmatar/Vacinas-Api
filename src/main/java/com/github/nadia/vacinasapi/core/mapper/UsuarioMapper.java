package com.github.nadia.vacinasapi.core.mapper;

import com.github.nadia.vacinasapi.api.DTO.request.UsuarioRequest;
import com.github.nadia.vacinasapi.api.DTO.request.UsuarioUpdateRequest;
import com.github.nadia.vacinasapi.api.DTO.response.UsuarioResponse;
import com.github.nadia.vacinasapi.domain.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioMapper {

//    public Usuario toEntity(UsuarioRequest request){
//        return new Usuario(null, request.getNome(), request.getCpf(), request.getEmail(), request.getDataNascimento());
//
//    }
//
//    public Usuario toEntityUsingBuilder(UsuarioRequest request){
//       return Usuario.builder()
//               .nome(request.getNome())
//               .cpf(request.getCpf())
//               .email(request.getEmail())
//               .dataNascimento(request.getDataNascimento())
//               .build();
//    }

    public Usuario toUsuarioEntity(UsuarioRequest request){
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setCpf(request.getCpf());
        usuario.setEmail(request.getEmail());
        usuario.setDataNascimento(request.getDataNascimento());
        return usuario;
    }

    public Usuario toUsuarioEntity(UsuarioUpdateRequest request){
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setCpf(request.getCpf());
        usuario.setEmail(request.getEmail());
        usuario.setDataNascimento(request.getDataNascimento());
        return usuario;
    }

    public UsuarioResponse toUsuarioResponse(Usuario usuario){
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setCpf(usuario.getCpf());
        response.setEmail(usuario.getEmail());
        response.setDataNascimento(usuario.getDataNascimento());
        return response;
    }

    public List<UsuarioResponse> toUsuarioResponseList(List<Usuario> usuarios){
        List<UsuarioResponse> responseList = new ArrayList<>();
        for (Usuario usuario : usuarios){
            UsuarioResponse response = this.toUsuarioResponse(usuario);
            responseList.add(response);
        }
        return responseList;
    }

}
