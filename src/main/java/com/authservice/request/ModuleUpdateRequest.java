package com.authservice.request;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for update a module in the system.")
public class ModuleUpdateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 50, message = "El nombre del módulo en castellano debe tener entre 3 y 50 caracteres.")
    @Schema(description = "Name of the module in Spanish.", example = "Gestión de Usuarios")
    private String nameEs;
    
    @Size(min = 3, max = 50, message = "El nombre del módulo en ingles debe tener entre 3 y 50 caracteres.")
    @Schema(description = "Name of the module in English.", example = "User Management")
    private String nameEn;

    @Size(max = 255, message = "La descripción del módulo en castellano no puede exceder los 255 caracteres.")
    @Schema(description = "Description of the module in Spanish.", example = "Módulo para gestionar usuarios y sus roles.", required = true)
    private String descriptionEs;
    
    @Size(max = 255, message = "La descripción del módulo en ingles no puede exceder los 255 caracteres.")
    @Schema(description = "Description of the module in English.", example = "Module for managing users and their roles.", required = true)
    private String descriptionEn;
    
    @Size(max = 20, message = "El prefix del módulo  no puede exceder los 20 caracteres.")
    @Schema(description = "Prefix of the module.", example = "AUTH")
    private String prefix;
}
