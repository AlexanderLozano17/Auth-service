package com.authservice.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.authservice.entities.ModuleEntity;

import jakarta.transaction.Transactional;


public interface ModuleJpaRepository extends JpaRepository<ModuleEntity, Long> {
	
	/**
	 * 
	 * @param prefix
	 * @return
	 */
	Optional<ModuleEntity> findByPrefix(String prefix);
	
	/**
	 * Recupera una lista de todas las entidades ModuleEntity que están activas (active = true).
	 *
	 * @return 
	 */
    @Query("SELECT m FROM ModuleEntity m WHERE m.active = true")
	List<ModuleEntity> findOnlyEnable();
	
    /**
	 * Recupera una lista de todas las entidades ModuleEntity que están inactivas (active = false).
	 *
	 * @return 
	 */
    @Query("SELECT m FROM ModuleEntity m WHERE m.active = false")
	List<ModuleEntity> findOnlyDisable();
	    
    /**
     * Realiza un "soft delete" (eliminado lógico) de un módulo por su ID.
     * Actualiza el campo 'active' a false y establece la fecha y hora de eliminación en 'deletedAt'.
     *
     * @param id El ID del módulo a eliminar lógicamente.
     */
    @Modifying // Indica que esta consulta modificará la base de datos (UPDATE, DELETE, INSERT)
    @Transactional // Asegura que la operación se ejecute dentro de una transacción
    @Query("UPDATE ModuleEntity m SET m.active = false, m.deletedAt = :deletedAt WHERE m.id = :id")
    void softDeleteById(Long id, LocalDateTime deletedAt);

}
