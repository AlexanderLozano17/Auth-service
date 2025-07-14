package com.authservice.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "permissions") 
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@ToString(callSuper = true) // Es importante para que toString incluya los campos de BaseEntity
@EqualsAndHashCode(callSuper = true) // Es importante para que equals/hashCode consideren los campos de BaseEntity (especialmente el id)
public class PermissionEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String name; // Ej: "product:read", "order:create", "user:delete"

    @Column(name = "description_es", nullable = false, length = 255)
    private String descriptionEs;
    
    @Column(name = "description_en", nullable = false, length = 255)
    private String descriptionEn;

    // --- Relación Many-to-One con ModuleEntity ---
    // Muchos permisos pueden pertenecer a un solo módulo
    // @JoinColumn especifica la columna FK en la tabla 'permissions' que apunta a 'modules'
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa para el módulo
    @JoinColumn(name = "module_id", nullable = false) // Columna 'module_id' en la tabla 'permissions'
    private ModuleEntity module; // La entidad ModuleEntity a la que pertenece este permiso

    // --- Relación con la tabla intermedia role_permissions ---
    // Un permiso puede estar en muchos RolePermission (relación de uno a muchos)
    // 'mappedBy' indica que la relación es controlada por el campo 'permission' en la entidad RolePermissionEntity
    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RolePermissionEntity> rolePermissions = new HashSet<>();
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

}