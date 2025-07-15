package com.authservice.response;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private LocalDateTime timestamp;

}
