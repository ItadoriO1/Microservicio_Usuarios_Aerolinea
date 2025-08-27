package com.example.servicio1.persistence.serviceImpl;

import com.example.servicio1.domain.dto.UsuarioDTO;
import com.example.servicio1.domain.repository.UsuarioRepository;
import com.example.servicio1.domain.service.UsuarioService;
import com.example.servicio1.exceptions.UsuarioNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Iterable<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioDTO getUsuarioById(Long id) {
        Optional<UsuarioDTO> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()){
            throw new UsuarioNotFoundException("usuario","id",id);
        }
        return usuario.get();
    }

    @Override
    public UsuarioDTO saveUsuario(UsuarioDTO usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDTO updateUsuario(UsuarioDTO usuario) {
        validateUsuarioExists(usuario.getId());
        return usuarioRepository.update(usuario);
    }

    @Override
    public void deleteUsuario(Long id) {
        validateUsuarioExists(id);
        usuarioRepository.delete(id);
    }

    @Override
    public long countUsuarios() {
        return usuarioRepository.count();
    }

    @Override
    public UsuarioDTO getUsuarioByEmail(String email) {
        Optional<UsuarioDTO> usuario = usuarioRepository.findByEmail(email);
        if(usuario.isEmpty()){
            throw new UsuarioNotFoundException("usuario","email",email);
        }
        return usuario.get();
    }

    @Override
    public UsuarioDTO getUsuarioByCedula(String cedula) {
        Optional<UsuarioDTO> usuario = usuarioRepository.findByCedula(cedula);
        if(usuario.isEmpty()){
            throw new UsuarioNotFoundException("usuario","cedula",cedula);
        }
        return usuario.get();
    }

    private void validateUsuarioExists(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException("propietario", "id", id);
        }
    }
}
