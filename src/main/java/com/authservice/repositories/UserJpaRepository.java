package com.authservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.entities.UserEntity;



public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	Optional<UserEntity> findByEmail(String email);

}
