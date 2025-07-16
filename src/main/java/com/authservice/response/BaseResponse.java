package com.authservice.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * La marca de tiempo (timestamp) en que se generó esta respuesta.
     * Útil para auditoría y para entender cuándo ocurrió el evento.
     */
	@Schema(description = "Timestamp request.", example = "2025-07-15T10:30:00.123")
    private LocalDateTime timestamp;
	
	// Helper para obtener la ruta de la petición ejecutada
    protected static String getCurrentRequestPath() {
        try {
            // Obtiene los atributos de la solicitud del hilo actual
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            // Retorna la URI de la solicitud
            return sra.getRequest().getRequestURI();
        } catch (IllegalStateException e) {
            // Si no hay una solicitud activa (ej. en pruebas unitarias o inicialización fuera de un contexto web)
            return null;
        }
    }

}
