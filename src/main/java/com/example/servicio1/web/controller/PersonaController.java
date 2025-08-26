package com.example.servicio1.web.controller;

import com.example.servicio1.domain.dto.PersonaDTO;
import com.example.servicio1.domain.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
@Tag(name = "Personas", description = "API para la gestion de personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    // Obtener todas las personas
    @Operation(summary = "obtener todas las personas", description = "Retorna una lista de las personas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de personas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<Iterable<PersonaDTO>> getAllPersonas(){
        Iterable<PersonaDTO> personas = personaService.getAllPersonas();
        return ResponseEntity.ok(personas);
    }

    //Obtener la Persona por ID
    @Operation(summary = "Obtener una persona por ID", description = "Retorna la persona correspondiente al ID proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> getPersonaById(@PathVariable @Parameter(description = "ID de la persona") Long id){
        Optional<PersonaDTO> personaOpt = personaService.getPersonaById(id);
        PersonaDTO personaDTO = personaOpt.orElseThrow(
                () -> new RuntimeException("No se encontro el persona con el ID " + id)
        );
        return  ResponseEntity.ok(personaDTO);
    }

    //Guardar una nueva persona
    @Operation(summary = "Crear una nueva persona", description = "Guarda una nueva persona en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud invalida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidar", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<PersonaDTO> savePersona(@RequestBody @Parameter(description = "Datos de la persona a crear") PersonaDTO personaDTO){
        PersonaDTO savePersonaDTO = personaService.savePersona(personaDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savePersonaDTO);
    }

    //Actualizar una persona existente
    @Operation(summary = "Actualizar una persona existente", description = "Actualiza los datos de una persona existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PersonaDTO> updatePersona(@PathVariable @Parameter(description = "ID de la persona") Long id,
                                                    @RequestBody @Parameter(description = "Datos de la persona actualizados") PersonaDTO personaDTO){
        personaDTO.setId(id);
        PersonaDTO updatePersonaDTO = personaService.updatePersona(personaDTO);
        return  ResponseEntity.ok(updatePersonaDTO);
    }

    //Eliminar una persona por ID
    @Operation(summary = "Eliminar una persona por ID", description = "Elimina la persona correspondiente al ID proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona eliminada exitosamente", content =  @Content),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PersonaDTO> deletePersona(@PathVariable @Parameter(description = "ID de la persona") Long id){
        personaService.deletePersona(id);
        return  ResponseEntity.ok().build();
    }

    //Contar el numero total de personas
    @Operation(summary = "Contar la cantidad de personas", description = "Retorna el total de personas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad de registros", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/count")
    public ResponseEntity<Long> countPersonas(){
        long count = personaService.countPersonas();
        return ResponseEntity.ok(count);
    }
}
