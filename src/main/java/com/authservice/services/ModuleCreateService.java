package com.authservice.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.authservice.dto.ModuleDto;
import com.authservice.entities.ModuleEntity;
import com.authservice.repositories.ModuleJpaRepository;
import com.authservice.usecases.ModuleCreateUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleCreateService implements ModuleCreateUseCase {
	
	private final ModuleJpaRepository moduleJpaRepository;


	@Override
	public Optional<ModuleDto> create(ModuleDto dto) {		
		//ModuleEntity entity = mapper.toEntity(dto);		
		
		ModuleEntity entity = new ModuleEntity();
		entity.setNameEs(dto.getNameEs());
		entity.setNameEn(dto.getNameEn());		
		entity.setDescriptionEs(dto.getDescriptionEs());
		entity.setDescriptionEn(dto.getDescriptionEn());		
		
		Optional<ModuleEntity> savedEntityOptional =  Optional.ofNullable(moduleJpaRepository.save(entity));
		
		ModuleEntity saveEntity = savedEntityOptional
				.orElseThrow(() -> new RuntimeException("Fallo al guardar el modulo.")); 

		ModuleDto moduleDto = new ModuleDto().builder()
				.id(saveEntity.getId())
				.nameEs(saveEntity.getNameEs())
				.nameEn(saveEntity.getNameEn())
				.descriptionEs(saveEntity.getDescriptionEs())
				.descriptionEn(saveEntity.getDescriptionEn())
				.active(saveEntity.getActive())
				.build();
		return Optional.ofNullable(moduleDto);
	}

}
