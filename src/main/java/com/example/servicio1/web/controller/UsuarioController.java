package com.example.servicio1.web.controller;

import com.example.servicio1.domain.dto.UsuarioDTO;
import com.example.servicio1.domain.service.UsuarioService;
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

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para la gestion de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Obtener todos los usuarios
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500")
    })
    @GetMapping("/all")
    public ResponseEntity<Iterable<UsuarioDTO>> getAllUsuarios() {
        Iterable<UsuarioDTO> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    //Obtener usuario por ID
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un admin segun el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud invalida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable @Parameter(description = "ID del usuario") long id) {
        UsuarioDTO usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    //Guardar un usuario
    @Operation(summary = "Crear un nuevo usuario", description = "Guardar un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud invalida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<UsuarioDTO> saveUsuario(@RequestBody @Parameter(description = "Datos del usuario") UsuarioDTO usuario) {
        UsuarioDTO usuarioDTO = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    //Actualizar un usuario
    @Operation(summary = "Actualizar un usuario por ID", description = "Actializa los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente", content =  @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("update/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@RequestBody @Parameter(description = "Datos actualizados del usuario") UsuarioDTO usuario,
                                                    @PathVariable @Parameter(description = "ID del usuario") long id) {
        usuario.setId(id);
        UsuarioDTO usuarioDTO = usuarioService.updateUsuario(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

    //Eliminar un usuario
    @Operation(summary = "Eliminar un usuario por ID", description = "Elimina un usuario del sistema segun el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UsuarioDTO> deleteUsuario(@PathVariable @Parameter(description = "ID del usuario") long id) {
        usuarioService.deleteUsuario(id);
        return  ResponseEntity.ok().build();
    }

    //Contar el numero total de admins
    @Operation(summary = "Contar la cantidad total de usuarios", description = "Retorna el total de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad de registros", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        long count = usuarioService.countUsuarios();
        return ResponseEntity.ok(count);
    }
}
