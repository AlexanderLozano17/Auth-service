package com.authservice.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.authservice.dto.ModuleDto;
import com.authservice.entities.ModuleEntity;
import com.authservice.mapper.ModuleMapper;
import com.authservice.repositories.ModuleJpaRepository;
import com.authservice.usecases.ModuleGetUseCase;
import com.authservice.utils.LogHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleGetService implements ModuleGetUseCase {
			
	private final ModuleJpaRepository jpaRepository;
	private final ModuleMapper mapper;

	@Override
	public Optional<ModuleDto> getById(Long id) {
		// TODO Auto-generated method stub		
		log.info(LogHelper.start(getClass(), "getById(id)"));
		
		Optional<ModuleEntity> entity = jpaRepository.findById(id);		
		return entity.map(module -> {
			ModuleDto moduleDto = mapper.toDto(module);
			return moduleDto;
		});
	}

	@Override
	public List<ModuleDto> getAll() {
		// TODO Auto-generated method stub
		log.info(LogHelper.start(getClass(), "getAll()"));
		
		return jpaRepository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<ModuleDto> getOnlyEnable() {
		// TODO Auto-generated method stub
		log.info(LogHelper.start(getClass(), "getOnlyEnable()"));
		
		return jpaRepository.findOnlyEnable().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<ModuleDto> getOnlyDisabled() {
		// TODO Auto-generated method stub
		log.info(LogHelper.start(getClass(), "getOnlyDisabled()"));
		
		return jpaRepository.findOnlyDisable().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

}
