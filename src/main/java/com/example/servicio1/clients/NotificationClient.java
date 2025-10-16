package com.example.servicio1.clients;

import com.example.servicio1.domain.dto.NotificacionRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NotificationClient {

    private final WebClient webClient;

    public NotificationClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/ServicioNotificaciones/api/notificaciones/save")
                .build();
    }

    public void enviarNotificacion(String person_id, String email, String nombre) {
        NotificacionRequest notificacion = new NotificacionRequest(
                person_id,
                email,
                "Registro exitoso",
                "Hola " + nombre + ", tu cuenta ha sido creada con éxito."
        );

        webClient.post()
                .bodyValue(notificacion)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
