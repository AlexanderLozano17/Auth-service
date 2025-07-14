package com.authservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.entities.ModuleEntity;


public interface ModuleJpaRepository extends JpaRepository<ModuleEntity, Long> {
	
	/**
	 * 
	 * @param prefix
	 * @return
	 */
	Optional<ModuleEntity> findByPrefix(String prefix);

}
