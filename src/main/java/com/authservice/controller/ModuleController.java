package com.authservice.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.controller.version.ApiVersionPaths;
import com.authservice.dto.ModuleDto;
import com.authservice.mapper.ModuleMapper;
import com.authservice.request.ModuleCreateRequest;
import com.authservice.response.Response;
import com.authservice.response.ResponseEasy;
import com.authservice.usecases.ModuleCreateUseCase;
import com.authservice.usecases.ModuleDeleteUseCase;
import com.authservice.usecases.ModuleGetUseCase;
import com.authservice.utils.LogHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiVersionPaths.V1_MODULE)
@Slf4j
@RequiredArgsConstructor
public class ModuleController {
	
	private final MessageSource messageSource;
	private final ModuleCreateUseCase createUseCase;
	private final ModuleGetUseCase getUseCase;
	private final ModuleDeleteUseCase deleteUseCase;
	private final ModuleMapper moduleMapper;
	
	@PostMapping
	public ResponseEntity<Response<ModuleDto>> createModule(@Validated @RequestBody ModuleCreateRequest request,
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "createModule(request)"));
		
		Optional<ModuleDto> moduleDto = createUseCase.create(moduleMapper.requesCreatetToDto(request));	
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("created.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(moduleDto.get(), successMessage));	
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<Response<ModuleDto>> getModuleById(@PathVariable Long id,
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "getModuleById(id)"));
	
		Optional<ModuleDto> moduleDto = getUseCase.getById(id);	
	
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("retrieved.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(moduleDto.get(), successMessage));
	}
	
	@GetMapping
	public ResponseEntity<Response<List<ModuleDto>>> getAllModule(@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "getAllModule()"));
		
		List<ModuleDto> listModuleDto = getUseCase.getAll();
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("retrieved.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(listModuleDto, successMessage));
	}
	
	@GetMapping("/enable")
	public ResponseEntity<Response<List<ModuleDto>>> getAOnlyEnableModule(@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "getAOnlyEnableModule()"));
		
		List<ModuleDto> listModuleEnable = getUseCase.getOnlyEnable();
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("retrieved.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(listModuleEnable, successMessage));
	}
	
	@GetMapping("/disabled")
	public ResponseEntity<Response<List<ModuleDto>>> getAOnlyDisabledModule(@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "getAOnlyDisabledModule()"));
		
		List<ModuleDto> listModuleDisabled = getUseCase.getOnlyDisabled();
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("retrieved.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(listModuleDisabled, successMessage));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseEasy<Void>> deleteModuelById(@PathVariable Long id, @RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "deleteModuelById()"));
		
		deleteUseCase.deleteById(id);
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("deleted.successfully", null, currentLocale);
		return ResponseEntity.ok(ResponseEasy.success(successMessage));
	}
	
	@DeleteMapping("/softdelete/{id}")
	public ResponseEntity<ResponseEasy<Void>> softDeleteModuelById(@PathVariable Long id, @RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "deleteModuelById()"));
		
		deleteUseCase.softDeleteById(id);
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("deleted.successfully", null, currentLocale);
		return ResponseEntity.ok(ResponseEasy.success(successMessage));
	}
	
}
