package com.authservice.usecases;

import java.util.Optional;

import com.authservice.dto.ModuleDto;
import com.authservice.entities.ModuleEntity;

public interface ModuleCreateUseCase {
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	Optional<ModuleDto> create(ModuleDto dto);
}
