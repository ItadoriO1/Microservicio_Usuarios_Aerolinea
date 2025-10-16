package com.example.servicio1.domain.dto;

public record NotificacionRequest(
         String person_id
        ,String email
        ,String titulo
        ,String mensaje) {
}
