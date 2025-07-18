package com.authservice.request;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a new module in the system.")
public class ModuleCreateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

    @NotBlank(message = "El nombre del módulo en castellano no puede estar vacío.") // No nulo y al menos un carácter no blanco
    @Size(min = 3, max = 50, message = "El nombre del módulo en castellano debe tener entre 3 y 50 caracteres.")
    @Schema(description = "Name of the module in Spanish.", example = "Gestión de Usuarios")
    private String nameEs;
    
    @NotBlank(message = "El nombre del módulo en ingles no puede estar vacío.") // No nulo y al menos un carácter no blanco
    @Size(min = 3, max = 50, message = "El nombre del módulo en ingles debe tener entre 3 y 50 caracteres.")
    @Schema(description = "Name of the module in English.", example = "User Management")
    private String nameEn;

    @Size(max = 255, message = "La descripción del módulo en castellano no puede exceder los 255 caracteres.")
    @NotBlank(message = "La descripción del módulo en castellano es obligatoria")
    @NotNull(message = "La descripción del módulo no puede ser nula")
    @Schema(description = "Description of the module in Spanish.", example = "Módulo para gestionar usuarios y sus roles.", required = true)
    private String descriptionEs;
    
    @Size(max = 255, message = "La descripción del módulo en ingles no puede exceder los 255 caracteres.")
    @NotBlank(message = "La descripción del módulo en ingles es obligatoria")
    @NotNull(message = "La descripción del módulo no puede ser nula")
    @Schema(description = "Description of the module in English.", example = "Module for managing users and their roles.", required = true)
    private String descriptionEn;
    
    @Size(max = 20, message = "El prefix del módulo  no puede exceder los 20 caracteres.")
    @NotBlank(message = "El prefix del módulo es obligatoria")
    @NotNull(message = "El prefix del módulo no puede ser nula")
    @Schema(description = "Prefix of the module.", example = "AUTH")
    private String prefix;
}