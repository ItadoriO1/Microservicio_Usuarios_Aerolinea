package com.example.servicio1.domain.service;

import com.example.servicio1.clients.NotificationClient;
import com.example.servicio1.domain.dto.LoginRequest;
import com.example.servicio1.domain.dto.PersonaDTO;
import com.example.servicio1.domain.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Obtener todos los registros
    public Iterable<PersonaDTO> getAllPersonas(){ return personaRepository.findAll(); }

    //Obtener una Persona por ID
    public Optional<PersonaDTO> getPersonaById(Long id){ return personaRepository.findById(id); }

    //Guardar una Persona
    public PersonaDTO savePersona(PersonaDTO persona){return personaRepository.save(persona);}

    //Actualizar una Persona existente
    public PersonaDTO updatePersona(PersonaDTO persona){
        if(!personaRepository.existsById(persona.getId())){
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

    //Obtener una persona por email
    public Optional<PersonaDTO> getPersonaByEmail(String email){ return personaRepository.findByEmail(email); }

    //Autentificar persona
    public PersonaDTO authenticate(LoginRequest loginRequest){
        Optional<PersonaDTO> optionalPersonaDTO = personaRepository.findByEmail(loginRequest.getEmail());
        // 1. Manejo: Email no encontrado -> Lanza excepción específica
        if (optionalPersonaDTO.isEmpty()){
            // Esto le dice al Controller que devuelva 404 o 401.
            throw new UsernameNotFoundException("Usuario no encontrado.");
        }
        PersonaDTO personaDTO = optionalPersonaDTO.get();
        // 2. Comparación de la contraseña
        boolean esValida = passwordEncoder.matches(loginRequest.getPassword(), personaDTO.getContrasenia());
        // 3. Manejo: Contraseña incorrecta -> Lanza excepción específica
        if (!esValida){
            // Esto le dice al Controller que devuelva 401 Unauthorized.
            throw new BadCredentialsException("Contraseña inválida.");
        }
        // 4. Limpieza y Éxito
        // Por seguridad, siempre limpia la contraseña antes de devolver el DTO al exterior
        personaDTO.setContrasenia(null);
        return personaDTO;
    }
}
