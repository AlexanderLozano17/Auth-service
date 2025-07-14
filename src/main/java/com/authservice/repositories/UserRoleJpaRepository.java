package com.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authservice.entities.UserRoleEntity;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity, Long> {

}
