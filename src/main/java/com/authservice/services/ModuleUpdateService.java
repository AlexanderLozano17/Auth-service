package com.authservice.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.authservice.dto.ModuleDto;
import com.authservice.entities.ModuleEntity;
import com.authservice.mapper.ModuleMapper;
import com.authservice.repositories.ModuleJpaRepository;
import com.authservice.usecases.ModuleUpdateUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleUpdateService implements ModuleUpdateUseCase {
	
	private final ModuleJpaRepository jpaRepository;
	private final ModuleMapper mapper;

	@Override
	public Optional<ModuleDto> update(Long id, ModuleDto dto) {
		// TODO Auto-generated method stub
		Optional<ModuleEntity> entity = jpaRepository.findById(id);
		
		if (entity.isEmpty()) return Optional.empty();
		
		ModuleEntity moduleEntity = entity.get();	
		
		if (dto.getNameEn() != null && !dto.getNameEn().isBlank()) moduleEntity.setNameEn(dto.getNameEn());			
		if (dto.getNameEs() != null && !dto.getNameEs().isBlank()) moduleEntity.setNameEs(dto.getNameEs());
		if (dto.getDescriptionEn() != null && !dto.getDescriptionEn().isBlank()) moduleEntity.setDescriptionEn(dto.getDescriptionEn());		
		if (dto.getDescriptionEs() != null && !dto.getDescriptionEs().isBlank()) moduleEntity.setDescriptionEs(dto.getDescriptionEs());
		if (dto.getPrefix() != null && !dto.getPrefix().isBlank()) moduleEntity.setPrefix(dto.getPrefix());
		moduleEntity.setUpdatedAt(LocalDateTime.now());
		
		ModuleEntity updateEntity = jpaRepository.save(moduleEntity);
		return Optional.ofNullable(mapper.toDto(updateEntity));
	}

	@Override
	public boolean enableModule(Long id) {
		// TODO Auto-generated method stub
		if (jpaRepository.existsById(id)) {
			jpaRepository.enableModule(id, LocalDateTime.now());
			return true;
		} 
		return false;
	}

	@Override
	public boolean disabledModule(Long id) {
		// TODO Auto-generated method stub
		if (jpaRepository.existsById(id)) {
			jpaRepository.disabledModule(id, LocalDateTime.now());
			return true;
		} 
		return false;
	}

}
