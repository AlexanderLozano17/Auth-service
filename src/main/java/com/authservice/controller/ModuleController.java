package com.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.controller.version.ApiVersionPaths;
import com.authservice.dto.ModuleDto;
import com.authservice.mapper.ModuleMapper;
import com.authservice.request.ModuleCreateRequest;
import com.authservice.usecases.ModuleCreateUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiVersionPaths.MODULE)
@Slf4j
@RequiredArgsConstructor
public class ModuleController {

	private final ModuleCreateUseCase createUseCase;
	//private final ModuleMapper moduleMapper;
	
	@PostMapping
	public ModuleDto createModule(@Valid @RequestBody ModuleCreateRequest request) {
		
		
		
	//	ModuleDto dto = moduleMapper.requesCreatetToDto(request);
		
		ModuleDto moduleDto = new ModuleDto().builder()
				.nameEs(request.getNameEs())
				.nameEn(request.getNameEn())
				.descriptionEs(request.getDescriptionEs())
				.descriptionEn(request.getDescriptionEn())
				.build();
		return createUseCase.create(moduleDto).get();
	}
}
