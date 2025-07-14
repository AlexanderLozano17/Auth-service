package com.authservice.usecases;

public interface ModuleDeleteUseCase {

	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean softDelete(Long id);
}
