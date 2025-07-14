package com.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.entities.PermissionEntity;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, Long> {

}
