package com.authservice.usecases;

import java.util.List;
import java.util.Optional;

import com.authservice.dto.ModuleDto;

public interface ModuleGetUseCase {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Optional<ModuleDto> getById(Long id);
	
	/**
	 * 
	 * @return
	 */
	List<ModuleDto> getAll();
	
	/**
	 * 
	 * @return
	 */
	List<ModuleDto> getOnlyEnable();
	
	/**
	 * 
	 * @return
	 */
	List<ModuleDto> getOnlyDisabled();
}
