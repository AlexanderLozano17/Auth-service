package com.authservice.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.authservice.repositories.ModuleJpaRepository;
import com.authservice.usecases.ModuleDeleteUseCase;
import com.authservice.utils.LogHelper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleDeleteService implements ModuleDeleteUseCase {
	
	private final ModuleJpaRepository jpaRepository;

	@Transactional
	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		log.info(LogHelper.start(getClass(), "deleteById(id)"));
		jpaRepository.deleteById(id);
		return true;
	}

	@Transactional
	@Override
	public boolean softDeleteById(Long id) {
		// TODO Auto-generated method stub
		log.info(LogHelper.start(getClass(), "softDeleteById(id)"));
		jpaRepository.softDeleteById(id, LocalDateTime.now());
		return true;
	}

}
