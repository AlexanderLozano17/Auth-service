package com.authservice.response;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true) // Es importante para que toString incluya los campos de BaseEntity
@EqualsAndHashCode(callSuper = true) // Es importante para que equals/hashCode consideren los campos de BaseEntity (especialmente el id)
@Builder
@Schema(
	    description = "Standard structure for API error responses.",
	    example = "{\n  \"timestamp\": \"2025-07-15T10:30:00.123\",\n  \"status\": 400,\n  \"error\": \"Bad Request\",\n  \"message\": \"Validation error in input data.\",\n  \"path\": \"/api/v1/modules\",\n  \"details\": {\"nameEs\": \"The 'nameEs' field cannot be empty.\"}\n}"
	)
public class ErrorResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Timestamp when the error occurred.", example = "2025-07-15T10:30:00.123")
	private LocalDateTime timestamp;

    @Schema(description = "HTTP status code of the error.", example = "400")
    private int status;

    @Schema(description = "HTTP error message (e.g., 'Bad Request', 'Not Found').", example = "Bad Request")
    private String error;

    @Schema(description = "General descriptive message of the error.", example = "Validation error in input data.")
    private String message;

    @Schema(description = "The request path (URI) that generated the error.", example = "/api/v1/modules")
    private String path;

    @Schema(description = "Specific error details, often for validation errors (field -> message).", nullable = true)
    private Map<String, String> details;
}
