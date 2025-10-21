package com.example.servicio1.clients;

import com.example.servicio1.domain.dto.NotificacionRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NotificationClient {

    private final WebClient webClient;

    public NotificationClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/ServicioNotificaciones/api/notificaciones")
                .build();
    }

    public void enviarNotificacion(String personId, String email, String nombre) {
        NotificacionRequest notificacion = new NotificacionRequest(
                personId,
                email,
                "Registro exitoso",
                "Hola " + nombre + ", tu cuenta ha sido creada con éxito."
        );

        System.out.println("➡️ Enviando notificación: " + notificacion);

        webClient.post()
                .uri("/save")
                .bodyValue(notificacion)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
