package com.authservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.entities.RoleEntity;


public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	Optional<RoleEntity> findByName(String name);

}
