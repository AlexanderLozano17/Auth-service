package com.authservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.authservice.dto.ModuleDto;
import com.authservice.entities.ModuleEntity;
import com.authservice.request.ModuleCreateRequest;

@Mapper(
	    componentModel = MappingConstants.ComponentModel.SPRING,
	    // Estrategia para ignorar propiedades nulas al actualizar.
	    // Si un campo en el DTO es nulo, no se actualizará el campo correspondiente en la entidad.
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
public interface ModuleMapper {

	/**
     * Convierte un ModuleDto a una nueva instancia de ModuleEntity.
     * Ideal para crear nuevas entidades a partir de los datos de entrada.
     *
     * - 'id' de la entidad se ignora porque se asume que es generado por la DB.
     * - Campos de auditoría (createdAt, updatedAt, deletedAt de BaseEntity) se ignoran,
     * ya que son manejados por JPA y no deberían venir en el DTO de creación.
     * - La colección 'permissions' se ignora para evitar mapeos accidentales
     * que no corresponden a la lógica de negocio de creación directa del módulo.
     */
	@Mapping(target = "id", ignore = true) 
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "active", ignore = true)
    @Mapping(target = "permissions", ignore = true) // Ignora la colección de permisos
	ModuleEntity toEntity(ModuleDto dto);
	
	/**
     * Actualiza una instancia existente de ModuleEntity con los datos de un ModuleDto.
     * Ideal para operaciones de actualización (PUT/PATCH).
     *
     * - 'id' y los campos de auditoría no se sobrescriben.
     * - La colección 'permissions' se ignora para no afectar las relaciones existentes
     * a menos que tengas un mapeo explícito para gestionarlas.
     * - 'NullValuePropertyMappingStrategy.IGNORE' en el @Mapper asegura que los campos nulos en el DTO
     * no sobrescriban los valores existentes en la entidad.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateEntityFromDto(ModuleDto dto, @MappingTarget ModuleEntity entity);
    
    /**
     * Convierte una instancia de ModuleEntity a un ModuleDto.
     * Ideal para enviar datos de módulos como respuesta al cliente.
     * MapStruct mapeará automáticamente los campos 'id', 'name', 'description', 'isActive'.
     * Si quisieras incluir campos de auditoría de BaseEntity en el DTO, necesitarías agregarlos
     * en ModuleDto y MapStruct los mapearía automáticamente si los nombres coinciden.
     */
    ModuleDto toDto(ModuleEntity entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    ModuleDto requesCreatetToDto(ModuleCreateRequest request);

}
