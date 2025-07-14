package com.authservice.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase Embeddable para la clave primaria compuesta de la entidad UserRoleEntity.
 * Combina el ID del usuario y el ID del rol.
 */
@Embeddable // Indica que esta clase es incrustable en otra entidad como parte de su clave primaria
@Data // Anotación de Lombok para generar getters, setters, equals, hashCode y toString
@NoArgsConstructor // Anotación de Lombok para generar un constructor sin argumentos
@AllArgsConstructor // Anotación de Lombok para generar un constructor con todos los argumentos
public class UserRoleId implements Serializable {

    private static final long serialVersionUID = 1L; // Recomendado para Serializable

    @Column(name = "user_id") // Asegúrate de que el nombre de la columna coincida con la DB
    private Long userId;

    @Column(name = "role_id") // Asegúrate de que el nombre de la columna coincida con la DB
    private Long roleId;

    // Es crucial sobrescribir equals() y hashCode() para asegurar el correcto funcionamiento de JPA
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}
