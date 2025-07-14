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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la relación intermedia entre roles y permisos.
 * Mapea a la tabla 'role_permissions' y contiene campos de auditoría.
 */
@Entity
@Table(name = "role_permissions")
@Data
@NoArgsConstructor 
@AllArgsConstructor 
@EntityListeners(AuditingEntityListener.class)
public class RolePermissionEntity {

	@EmbeddedId // Indica que la clave primaria de esta entidad es una clave compuesta definida por RolePermissionId
	private RolePermissionId id;
	 
	// --- Relación Many-to-One con RoleEntity ---
	// Muchos RolePermissionEntity a un solo RoleEntity
	// @MapsId("roleId") mapea el campo 'roleId' de RolePermissionId a esta FK
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("roleId") // "roleId" debe coincidir con el nombre del campo en RolePermissionId
    @JoinColumn(name = "role_id", nullable = false) // Columna FK en la tabla role_permissions
    private RoleEntity role; // La entidad RoleEntity a la que se enlaza

    // --- Relación Many-to-One con PermissionEntity ---
    // Muchos RolePermissionEntity a un solo PermissionEntity
    // @MapsId("permissionId") mapea el campo 'permissionId' de RolePermissionId a esta FK
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId") // "permissionId" debe coincidir con el nombre del campo en RolePermissionId
    @JoinColumn(name = "permission_id", nullable = false) // Columna FK en la tabla role_permissions
    private PermissionEntity permission; // La entidad PermissionEntity a la que se enlaza

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
        
    // --- Campos de Auditoría ---
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Puedes añadir 'deleted_at' para borrado lógico si lo deseas
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    
    // Nuevo constructor para facilitar la creación
    public RolePermissionEntity(RoleEntity role, PermissionEntity permission) {
        this.role = role;
        this.permission = permission;
        // Es crucial que el ID compuesto se inicialice con los IDs de las entidades relacionadas
        // cuando se usa @MapsId.
        this.id = new RolePermissionId(role.getId(), permission.getId());
    }
    
    protected void onDelete() {
        this.deletedAt = LocalDateTime.now();
        this.isActive = false;
    }

}
