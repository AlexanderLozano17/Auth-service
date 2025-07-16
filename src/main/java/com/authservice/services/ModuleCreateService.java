package com.authservice.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.authservice.dto.ModuleDto;
import com.authservice.entities.ModuleEntity;
import com.authservice.mapper.ModuleMapper;
import com.authservice.repositories.ModuleJpaRepository;
import com.authservice.usecases.ModuleCreateUseCase;
import com.authservice.utils.LogHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleCreateService implements ModuleCreateUseCase {
	
	private final ModuleJpaRepository moduleJpaRepository;
	private final ModuleMapper mapper;

	@Override
	public Optional<ModuleDto> create(ModuleDto dto) {	
		log.info(LogHelper.start(getClass(), "create(dto)"));
		
		ModuleEntity entity = mapper.toEntity(dto);		
		
		Optional<ModuleEntity> savedEntityOptional =  Optional.ofNullable(moduleJpaRepository.save(entity));
		
		ModuleEntity saveEntity = savedEntityOptional
				.orElseThrow(() -> new RuntimeException("Fallo al guardar el modulo.")); 

		ModuleDto moduleDto = mapper.toDto(saveEntity);
		return Optional.ofNullable(moduleDto);
	}

}
