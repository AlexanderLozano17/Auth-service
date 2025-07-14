package com.authservice.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad que representa un rol en el sistema.
 * Mapea a la tabla 'roles' en la base de datos.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor 
@ToString(callSuper = true) // Es importante para que toString incluya los campos de BaseEntity
@EqualsAndHashCode(callSuper = true) // Es importante para que equals/hashCode consideren los campos de BaseEntity (especialmente el id)
public class RoleEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name; // Ej: "ROLE_ADMIN", "ROLE_PRODUCT_MANAGER"

    @Column(name = "description_es", nullable = false, length = 255) 
    private String descriptionEs;
    
    @Column(name = "description_en", nullable = false, length = 255) 
    private String descriptionEn;

    // --- Relación con la tabla intermedia user_roles ---
    // Un rol puede estar asignado a muchos UserRole (relación de uno a muchos)
    // 'mappedBy' indica que la relación es controlada por el campo 'role' en la entidad UserRole
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserRoleEntity> userRoles = new HashSet<>();

    // --- Relación con la tabla intermedia role_permissions ---
    // Un rol puede tener muchos RolePermission (relación de uno a muchos)
    // 'mappedBy' indica que la relación es controlada por el campo 'role' en la entidad RolePermissionEntity
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RolePermissionEntity> rolePermissions = new HashSet<>();

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
