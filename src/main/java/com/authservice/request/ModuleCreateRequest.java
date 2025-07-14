package com.authservice.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleCreateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

    @NotBlank(message = "El nombre del módulo en castellano no puede estar vacío.") // No nulo y al menos un carácter no blanco
    @Size(min = 3, max = 50, message = "El nombre del módulo en castellano debe tener entre 3 y 50 caracteres.")
    private String nameEs;
    
    @NotBlank(message = "El nombre del módulo en ingles no puede estar vacío.") // No nulo y al menos un carácter no blanco
    @Size(min = 3, max = 50, message = "El nombre del módulo en ingles debe tener entre 3 y 50 caracteres.")
    private String nameEn;

    @Size(max = 255, message = "La descripción del módulo en castellano no puede exceder los 255 caracteres.")
    @NotBlank(message = "La descripción del módulo en castellano es obligatoria")
    @NotNull(message = "La descripción del módulo no puede ser nula")
    private String descriptionEs;
    
    @Size(max = 255, message = "La descripción del módulo en ingles no puede exceder los 255 caracteres.")
    @NotBlank(message = "La descripción del módulo en ingles es obligatoria")
    @NotNull(message = "La descripción del módulo no puede ser nula")
    private String descriptionEn;

   }
