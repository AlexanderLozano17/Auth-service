package com.authservice.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users") 
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@ToString(callSuper = true) // Es importante para que toString incluya los campos de BaseEntity
@EqualsAndHashCode(callSuper = true) // Es importante para que equals/hashCode consideren los campos de BaseEntity (especialmente el id)
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

	@Column(name = "username", unique = true, nullable = false, length = 255)
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255) 
    private String password;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true; 

    // --- Relación con la tabla intermedia user_roles ---
    // Un usuario puede tener muchos UserRole (relación de uno a muchos)
    // 'mappedBy' indica que la relación es controlada por el campo 'user' en la entidad UserRole
    // CascadeType.ALL: Operaciones de persistencia (persist, merge, remove, refresh, detach) se propagan
    // orphanRemoval: Si un UserRole se desvincula de un User, se elimina de la base de datos
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserRoleEntity> userRoles = new HashSet<>(); // Usa Set para evitar duplicados y mejorar el rendimiento

}