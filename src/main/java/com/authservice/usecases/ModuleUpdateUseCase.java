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
	Optional<ModuleDto> update(Long id, ModuleDto fto);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean enableModule(Long id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean disabledModule(Long id);
}
