package com.authservice.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la relación intermedia entre usuarios y roles.
 * Mapea a la tabla 'user_roles' y contiene campos de auditoría.
 */
@Entity
@Table(name = "user_roles") 
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserRoleEntity {

	@EmbeddedId // Indica que la clave primaria de esta entidad es una clave compuesta definida por UserRoleId
    private UserRoleId id;

    // --- Relación Many-to-One con UserEntity ---
    // Muchos UserRoleEntity a un solo UserEntity
    // @MapsId("userId") mapea el campo 'userId' de UserRoleId a esta FK
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // "userId" debe coincidir con el nombre del campo en UserRoleId
    @JoinColumn(name = "user_id", nullable = false) // Columna FK en la tabla user_roles
    private UserEntity user; // La entidad UserEntity a la que se enlaza

    // --- Relación Many-to-One con RoleEntity ---
    // Muchos UserRoleEntity a un solo RoleEntity
    // @MapsId("roleId") mapea el campo 'roleId' de UserRoleId a esta FK
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId") // "roleId" debe coincidir con el nombre del campo en UserRoleId
    @JoinColumn(name = "role_id", nullable = false) // Columna FK en la tabla user_roles
    private RoleEntity role; // La entidad RoleEntity a la que se enlaza

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    
    // --- Campos de Auditoría ---
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Puedes añadir 'deleted_at' para borrado lógico si lo deseas
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    // Nuevo constructor para facilitar la creación
    public UserRoleEntity(UserEntity user, RoleEntity role) {
        this.user = user;
        this.role = role;
        // Es crucial que el ID compuesto se inicialice con los IDs de las entidades relacionadas
        // cuando se usa @MapsId.
        this.id = new UserRoleId(user.getId(), role.getId());
    }
    
    protected void onDelete() {
        this.deletedAt = LocalDateTime.now();
        this.isActive = false;
    }
}
