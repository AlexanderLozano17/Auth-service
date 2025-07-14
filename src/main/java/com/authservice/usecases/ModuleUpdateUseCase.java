package com.authservice.usecases;

import java.util.Optional;

import com.authservice.dto.ModuleDto;
import com.authservice.entities.ModuleEntity;

public interface ModuleUpdateUseCase {

	/**
	 * 
	 * @param id
	 * @param entity
	 * @return
	 */
	Optional<ModuleDto> update(Long id, ModuleEntity entity);
	
	/**
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	boolean updateModuleStatus(Long id, boolean value);
}
