package com.example.servicio1.persistence.repositoryImpl;

import com.example.servicio1.domain.dto.PersonaDTO;
import com.example.servicio1.domain.repository.PersonaRepository;
import com.example.servicio1.persistence.crud.PersonaCrudRepository;
import com.example.servicio1.persistence.entity.Persona;
import com.example.servicio1.persistence.mapper.PersonaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonaRepositoryImpl implements PersonaRepository {

    @Autowired
    private PersonaCrudRepository personaCrudRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonaMapper personaMapper;


    @Override
    public Iterable<PersonaDTO> findAll() {
        Iterable<Persona> personas = personaCrudRepository.findAll();
        return ((List<Persona>) personas).stream().map(personaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonaDTO> findById(Long id) {
        return personaCrudRepository.findById(id).
                map(personaMapper::toDTO);
    }

    @Override
    public PersonaDTO save(PersonaDTO personaDTO) {
        Persona persona = personaMapper.toEntity(personaDTO);
        if(persona.getId() != null && existsById(persona.getId())){
            throw new IllegalArgumentException("El registro ya existe");
        }
        String hashed = passwordEncoder.encode(personaDTO.getContrasenia());
        personaDTO.setContrasenia(hashed);

        Persona savedPersona = personaCrudRepository.save(persona);
        return personaMapper.toDTO(savedPersona);

    }

    @Override
    public PersonaDTO update(PersonaDTO personaDTO) {
        Persona persona = personaMapper.toEntity(personaDTO);
        if(existsById(persona.getId())){
            Persona updatePersona = personaCrudRepository.save(persona);
            return personaMapper.toDTO(updatePersona);
        }
        throw new IllegalArgumentException("El registro no existe");
    }

    @Override
    public void delete(Long id) {
        if(existsById(id)){
            personaCrudRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("El registro no existe");
        }
    }

    @Override
    public boolean existsById(Long id) {
        return personaCrudRepository.existsById(id);
    }

    @Override
    public long count() {
        return personaCrudRepository.count();
    }

    @Override
    public Optional<PersonaDTO> findByEmail(String email) {
        Optional<Persona> persona = personaCrudRepository.findByEmail(email);
        return persona.map(personaMapper::toDTO);
    }

    @Override
    public Optional<PersonaDTO> findByCedula(String cedula) {
        Optional<Persona> persona = personaCrudRepository.findByCedula(cedula);
        return persona.map(personaMapper::toDTO);
    }
}
