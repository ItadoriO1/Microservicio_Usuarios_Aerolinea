package com.example.servicio1.domain.service;

import com.example.servicio1.domain.dto.PersonaDTO;
import com.example.servicio1.domain.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    //Obtener todos los registros
    public Iterable<PersonaDTO> getAllPersonas(){ return personaRepository.findAll(); }

    //Obtener una Persona por ID
    public Optional<PersonaDTO> getPersonaById(Long id){ return personaRepository.findById(id); }

    //Guardar una Persona
    public PersonaDTO savePersona(PersonaDTO persona){ return personaRepository.save(persona); }

    //Actualizar una Persona existente
    public PersonaDTO updatePersona(PersonaDTO persona){
        if(personaRepository.existsById(persona.getId())){
            throw new RuntimeException("La persona con ID " + persona.getId() + " no existe");
        }
        return personaRepository.update(persona);
    }

    //Eliminar a una Persona por ID
    public void deletePersona(Long id){
        if(personaRepository.existsById(id)){
            personaRepository.delete(id);
        } else {
            throw new RuntimeException("La persona con ID " + id + " no existe");
        }
    }

    //Contar la cantidad de registros
    public Long countPersonas() { return personaRepository.count(); }
}
