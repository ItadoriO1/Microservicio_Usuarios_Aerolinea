package com.example.servicio1.web.controller;

import com.example.servicio1.domain.dto.AdminDTO;
import com.example.servicio1.domain.service.AdminService;
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
@RequestMapping("/api/admins")
@Tag(name = "Admins", description = "API para la gestion de usuarios")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //Obtener todos los admins
    @Operation(summary = "Obtener todos los admins", description = "Retorna una lista de admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de admins", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<Iterable<AdminDTO>> getAllAdmins() {
        Iterable<AdminDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    //Obtener admin por ID
    @Operation(summary = "Obtener un admin por id proporcionado", description = "Retorna un admin segun el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin encontrado", content = @Content(mediaType = "applicarion/json", schema = @Schema(implementation = AdminDTO.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud invalida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable @Parameter(description = "ID del admin") Long id){
        AdminDTO admin = adminService.getAdminById(id);
        return  ResponseEntity.ok(admin);
    }

    //Guardar un admin
    @Operation(summary = "Crear un nuevo admin", description = "Guardar un nuevo admin en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminDTO.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud invalida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<AdminDTO> saveAdmin(@RequestBody @Parameter(description = "Datos del admin") AdminDTO admin){
        AdminDTO saveAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveAdmin);
    }

    //Actualizar un admin por ID
    @Operation(summary = "Actualizar un admin por ID", description = "Actualiza los datos de un admin existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida", content =  @Content),
            @ApiResponse(responseCode = "404", description = "Admin no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable @Parameter(description = "ID del admin") Long id,
                                                @RequestBody @Parameter(description = "Datos del admin actualizados") AdminDTO admin){
        admin.setId(id);
        AdminDTO updateAdmin = adminService.updateAdmin(admin);
        return ResponseEntity.ok(updateAdmin);
    }

    //Eliminar Admin
    @Operation(summary = "Eliminar un admin por ID", description = "Elimina un admin segun el ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin eliminado correctamentre", content =  @Content),
            @ApiResponse(responseCode = "404", description = "Admin no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AdminDTO> deleteAdmin(@PathVariable @Parameter(description = "ID del admin") Long id){
        adminService.deleteAdmin(id);
        return ResponseEntity.ok().build();
    }

    //Contar el numero total de admins
    @Operation(summary = "Contal la cantidad de admins", description = "Retorna el total de admins registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad de registros", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/count")
    public ResponseEntity<Long> getAdminCount(){
        long count = adminService.countAdmins();
        return ResponseEntity.ok(count);
    }
}
