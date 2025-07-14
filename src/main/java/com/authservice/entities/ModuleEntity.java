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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "modules") 
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@ToString(callSuper = true) // Es importante para que toString incluya los campos de BaseEntity
@EqualsAndHashCode(callSuper = true) // Es importante para que equals/hashCode consideren los campos de BaseEntity (especialmente el id)
public class ModuleEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name_es", unique = true, nullable = false, length = 50)
    private String nameEs; // Nombre en español

    @Column(name = "name_en", unique = true, nullable = false, length = 50)
    private String nameEn; // Nombre en inglés

    @Column(name = "description_es", nullable = false, length = 255)
    private String descriptionEs; // Descripción en español

    @Column(name = "description_en", nullable = false, length = 255)
    private String descriptionEn; // Descripción en inglés
    
    @Column(name = "prefix", unique = true, nullable = false, length = 20) 
    private String prefix;
    
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    // --- Relación con PermissionEntity ---
    // Un módulo puede tener muchos permisos (relación de uno a muchos)
    // 'mappedBy' indica que la relación es controlada por el campo 'module' en la entidad PermissionEntity
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PermissionEntity> permissions = new HashSet<>();

}
