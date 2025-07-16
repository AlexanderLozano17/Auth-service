package com.authservice.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor 
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true) 
@SuperBuilder
@Schema(
	    description = "Standard structure for API responses, encapsulating the result of an operation.",
	    example = "{\n  \"success\": true,\n  \"statusCode\": 200,\n  \"message\": \"Operation performed successfully.\",\n  \"errors\": []\n}"
	)
public class ResponseEasy<T> extends BaseResponse {

	private static final long serialVersionUID = 1L;

	/**
     * Indica si la operación solicitada fue exitosa.
     * - `true` si la operación se completó sin errores lógicos o de negocio.
     * - `false` si hubo algún error (ej. validación, negocio, interno).
     */
	 @Schema(description = "Indicates whether the operation was successful.", example = "true")
	 private boolean success;

    /**
     * Un código de estado HTTP que refleja el resultado de la operación.
     * Ejemplos: 200 (OK), 201 (Created), 400 (Bad Request), 404 (Not Found), 500 (Internal Server Error).
     */
	 @Schema(description = "HTTP status code of the response.", example = "200")
	 private int statusCode;

    /**
     * Un mensaje descriptivo que proporciona información sobre el resultado de la operación.
     * Puede ser un mensaje de éxito ("Usuario creado exitosamente") o un mensaje de error ("Credenciales inválidas").
     */
	 @Schema(description = "Descriptive message of the operation's result.", example = "Resource retrieved successfully.")
	 private String message;

	/**
	 * La ruta de la solicitud (URI) que generó esta respuesta. Útil para depuración
	 * y seguimiento de errores.
	 */
	@Schema(description = "The request path that generated this response.", example = "/api/v1/modules/1")
	private String path;
    
    /**
     * Una lista de mensajes de error detallados.
     * Se utiliza cuando `success` es `false` y `message` proporciona una visión general.
     * Cada elemento de la lista puede ser un mensaje de validación específico o un detalle de error.
     * Se inicializa como una lista vacía para evitar NullPointerExceptions.
     */
    @Builder.Default // Asegura que Lombok Builder inicialice esta lista si no se especifica
    @Schema(description = "List of detailed error messages, present if the operation was not successful.", example = "[\"The 'name' field is required.\"]")
    private List<String> errors = new ArrayList<>();
    
    /**
     * Crea una respuesta de éxito sin datos, solo con un mensaje.
     *
     * @param message El mensaje de éxito.
     * @return Una instancia de Response configurada para éxito.
     */
    public static ResponseEasy<Void> success(String message) {
        return ResponseEasy.<Void>builder()
                .success(true)
                .statusCode(200) // OK
                .message(message)
                .path(getCurrentRequestPath())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Crea una respuesta de error con un mensaje y un código de estado específico.
     *
     * @param statusCode El código de estado HTTP del error.
     * @param message El mensaje general del error.
     * @return Una instancia de Response configurada para error.
     */
    public static ResponseEasy<Void> error(int statusCode, String message) {
        return ResponseEasy.<Void>builder()
                .success(false)
                .statusCode(statusCode)
                .message(message)
                .path(getCurrentRequestPath())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Crea una respuesta de error con un mensaje general y una lista de errores detallados.
     *
     * @param statusCode El código de estado HTTP del error.
     * @param message El mensaje general del error.
     * @param errors Una lista de mensajes de error específicos.
     * @return Una instancia de Response configurada para error con detalles.
     */
    public static ResponseEasy<Void> error(int statusCode, String message, List<String> errors) {
        return ResponseEasy.<Void>builder()
                .success(false)
                .statusCode(statusCode)
                .message(message)
                .path(getCurrentRequestPath())
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
