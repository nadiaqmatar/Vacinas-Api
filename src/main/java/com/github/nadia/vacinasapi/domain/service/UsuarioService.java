package com.github.nadia.vacinasapi.domain.service;

import com.github.nadia.vacinasapi.domain.entity.Usuario;
import com.github.nadia.vacinasapi.domain.entity.Vacina;
import com.github.nadia.vacinasapi.domain.exception.NotFoundException;
import com.github.nadia.vacinasapi.domain.exception.ServiceException;
import com.github.nadia.vacinasapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario){
        var usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());
        var usuarioCpf = usuarioRepository.findByCpf(usuario.getCpf());
        if (usuarioEmail.isPresent()){
            throw new ServiceException("Email já cadastrado!");
        }
        if (usuarioCpf.isPresent()){
            throw new ServiceException("Cpf já cadastrado!");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(()-> new NotFoundException("Usuário não encontrado."));
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        var usuarioNoBanco = check(id);
        usuario.setId(usuarioNoBanco.getId());
        if (!usuario.getEmail().isEmpty()) {
            if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
                throw new ServiceException("Email já cadastrado.");
            }
        }
        if (!usuario.getCpf().isEmpty()) {
            if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
                throw new ServiceException("CPF já cadastrado.");
            }
        }
        if (usuario.getNome() == null){
            usuario.setNome(usuarioNoBanco.getNome());
        }
        if (usuario.getDataNascimento() == null){
            usuario.setDataNascimento(usuarioNoBanco.getDataNascimento());
        }
        return usuarioRepository.save(usuario);
    }


    public List<Vacina> listarVacinas(Long id){
        return check(id).getVacinas();
    }

    public void deletar(Long id){
        check(id);
        usuarioRepository.deleteById(id);
    }

    private Usuario check(Long id){
        return usuarioRepository.findById(id).orElseThrow(()-> new ServiceException("Usuário não encontrado."));
    }
}
