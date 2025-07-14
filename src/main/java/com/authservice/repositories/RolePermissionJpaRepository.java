package com.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.entities.RolePermissionEntity;

public interface RolePermissionJpaRepository extends JpaRepository<RolePermissionEntity, Long>{

}
