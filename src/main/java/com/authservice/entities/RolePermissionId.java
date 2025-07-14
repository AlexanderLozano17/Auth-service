package com.authservice.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase Embeddable para la clave primaria compuesta de la entidad RolePermissionEntity.
 * Combina el ID del rol y el ID del permiso.
 */
@Embeddable // Indica que esta clase es incrustable en otra entidad como parte de su clave primaria
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class RolePermissionId implements Serializable {
	private static final long serialVersionUID = 1L; // Recomendado para Serializable

    @Column(name = "role_id") // Asegúrate de que el nombre de la columna coincida con la DB
    private Long roleId;

    @Column(name = "permission_id") // Asegúrate de que el nombre de la columna coincida con la DB
    private Long permissionId;

    // Es crucial sobrescribir equals() y hashCode() para asegurar el correcto funcionamiento de JPA
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionId that = (RolePermissionId) o;
        return Objects.equals(roleId, that.roleId) &&
               Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);
    }
}