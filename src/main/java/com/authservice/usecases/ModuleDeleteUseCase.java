package com.authservice.usecases;

public interface ModuleDeleteUseCase {

	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteById(Long id);
	
	/**
	 * Realiza un "soft delete" (eliminado lógico) de un módulo por su ID.
     * Actualiza el campo 'active' a false y establece la fecha y hora de eliminación en 'deletedAt'
     * 
	 * @param id
	 * @return
	 */
	boolean softDeleteById(Long id);
}
