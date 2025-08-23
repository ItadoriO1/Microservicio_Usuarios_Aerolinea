package com.example.servicio1.web.controller;

import com.example.servicio1.domain.dto.PersonaDTO;
import com.example.servicio1.domain.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personas")
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
}
