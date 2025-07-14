package com.authservice.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass // Indica que esta clase no es una entidad, pero sus atributos son heredados por las entidades
@Getter // Genera getters para todos los campos
@Setter // Genera setters para todos los campos
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@ToString(callSuper = true) // Incluye los campos de la superclase en toString
@EqualsAndHashCode(of = "id") // Genera equals y hashCode basados ÚNICAMENTE en el ID
@EntityListeners(AuditingEntityListener.class) // Habilita la auditoría de Spring Data JPA
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    // --- Campos de Auditoría ---
	@CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Anotación de Spring Data JPA para la fecha de última actualización
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Puedes añadir 'deleted_at' para borrado lógico si lo deseas
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    protected void onDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
