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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.controller.version.ApiVersionPaths;
import com.authservice.dto.ModuleDto;
import com.authservice.entities.ModuleEntity;
import com.authservice.mapper.ModuleMapper;
import com.authservice.request.ModuleCreateRequest;
import com.authservice.request.ModuleUpdateRequest;
import com.authservice.response.Response;
import com.authservice.response.ResponseEasy;
import com.authservice.usecases.ModuleCreateUseCase;
import com.authservice.usecases.ModuleDeleteUseCase;
import com.authservice.usecases.ModuleGetUseCase;
import com.authservice.usecases.ModuleUpdateUseCase;
import com.authservice.utils.LogHelper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiVersionPaths.V1_MODULE)
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Module Management", description = "APIs for creating, retrieving, and deleting modules")
public class ModuleController {
	
	private final MessageSource messageSource;
	private final ModuleCreateUseCase createUseCase;
	private final ModuleUpdateUseCase updateUseCase;
	private final ModuleGetUseCase getUseCase;
	private final ModuleDeleteUseCase deleteUseCase;
	private final ModuleMapper moduleMapper;
	
	@Operation(summary = "Create a new module", description = "Creates a new module based on the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Module created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
	@PostMapping
	public ResponseEntity<Response<ModuleDto>> createModule(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Data module update.",
					required = true,
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ModuleCreateRequest.class) 
					)
				)
			@Validated @RequestBody ModuleCreateRequest request,
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {

		log.info(LogHelper.start(getClass(), "createModule(request)"));
		
		Optional<ModuleDto> moduleDto = createUseCase.create(moduleMapper.requesCreateToDto(request));	
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("created.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(moduleDto.get(), successMessage));	
	}
	
	@Operation(summary = "Get a module by ID", description = "Retrieves a specific module by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Module retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Module not found with the provided ID",
                    content = @Content)
    })
	@GetMapping("/{id}")
	public ResponseEntity<Response<ModuleDto>> getModuleById(
			@Parameter(description = "ID of the module to retrieve")
			@PathVariable Long id,
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "getModuleById(id)"));
	
		Optional<ModuleDto> moduleDto = getUseCase.getById(id);	
	
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("retrieved.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(moduleDto.get(), successMessage));
	}
	
	@Operation(summary = "Get all modules", description = "Retrieves a list of all existing modules.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of modules retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)))
    })
	@GetMapping
	public ResponseEntity<Response<List<ModuleDto>>> getAllModule(
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "getAllModule()"));
		
		List<ModuleDto> listModuleDto = getUseCase.getAll();
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("retrieved.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(listModuleDto, successMessage));
	}
	
	@Operation(summary = "Get all enabled modules", description = "Retrieves a list of all modules that are currently enabled.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of enabled modules retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)))
    })
	@GetMapping("/enable")
	public ResponseEntity<Response<List<ModuleDto>>> getAOnlyEnableModule(
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "getAOnlyEnableModule()"));
		
		List<ModuleDto> listModuleEnable = getUseCase.getOnlyEnable();
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("retrieved.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(listModuleEnable, successMessage));
	}
	
	@Operation(summary = "Get all disabled modules", description = "Retrieves a list of all modules that are currently disabled.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of disabled modules retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)))
    })
	@GetMapping("/disabled")
	public ResponseEntity<Response<List<ModuleDto>>> getAOnlyDisabledModule(
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "getAOnlyDisabledModule()"));
		
		List<ModuleDto> listModuleDisabled = getUseCase.getOnlyDisabled();
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("retrieved.successfully", null, currentLocale);
		return ResponseEntity.ok(Response.success(listModuleDisabled, successMessage));
	}

	@Operation(summary = "Update a module")
	@ApiResponses(value = {
			@ApiResponse(
				responseCode = "200",
				description = "Updated successfully.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))
			)
		})
	@PutMapping("/{id}")
	public ResponseEntity<Response<ModuleDto>> updateModule(
			@Parameter(description = "ID of the module to update") 
			@PathVariable Long id, 
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Data module update.",
					required = true,
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ModuleUpdateRequest.class) 
					)
				)
			@Validated @RequestBody ModuleUpdateRequest request,
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "updateModule(id, request)"));
		
		ModuleDto moduleDto = moduleMapper.requesUpdateToDto(request);
		Optional<ModuleDto> dto = updateUseCase.update(id, moduleDto);
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("updated.successfully", null, currentLocale);
		
		return ResponseEntity.ok(Response.success(dto.get(), successMessage));
	}
	
	@Operation(summary = "enable a module")
	@ApiResponses(value = {
			@ApiResponse(
				responseCode = "200",
				description = "Updated successfully.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))
			)
		})
	@PutMapping("/enable/{id}")
	public ResponseEntity<Response<ModuleDto>> enableModule(
			@Parameter(description = "ID of the module to enable")
			@PathVariable Long id,
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "enableModule(id)"));
		
		updateUseCase.enableModule(id);
		Optional<ModuleDto> dto = getUseCase.getById(id);
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("updated.successfully", null, currentLocale);
		
		return ResponseEntity.ok(Response.success(dto.get(), successMessage));
	}
	
	@Operation(summary = "disabled a module")
	@ApiResponses(value = {
			@ApiResponse(
				responseCode = "200",
				description = "Updated successfully.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))
			)
		})
	@PutMapping("/disabled/{id}")
	public ResponseEntity<Response<ModuleDto>> disabledModule(
			@Parameter(description = "ID of the module to disabled")
			@PathVariable Long id,
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "disabledModule(id)"));
		
		updateUseCase.disabledModule(id);
		Optional<ModuleDto> dto = getUseCase.getById(id);
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("updated.successfully", null, currentLocale);
		
		return ResponseEntity.ok(Response.success(dto.get(), successMessage));
	}
	
	@Operation(summary = "Delete a module by ID (Hard Delete)", description = "Permanently deletes a module from the database using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Module deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEasy.class))),
            @ApiResponse(responseCode = "404", description = "Module not found with the provided ID",
                    content = @Content)
    })
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseEasy<Void>> deleteModuelById(
			@Parameter(description = "ID of the module to delete") 
			@PathVariable Long id, 
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "deleteModuelById(id)"));
		
		deleteUseCase.deleteById(id);
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("deleted.successfully", null, currentLocale);
		return ResponseEntity.ok(ResponseEasy.success(successMessage));
	}
	
	@Operation(summary = "Soft delete a module by ID", description = "Marks a module as inactive (soft delete) using its ID, without permanently removing it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Module soft-deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEasy.class))),
            @ApiResponse(responseCode = "404", description = "Module not found with the provided ID",
                    content = @Content)
    })
	@DeleteMapping("/softdelete/{id}")
	public ResponseEntity<ResponseEasy<Void>> softDeleteModuelById(
			@Parameter(description = "ID of the module to soft-delete") 
			@PathVariable Long id, 
			@Parameter(description = "Language for response messages (e.g., 'en', 'es')") 
			@RequestParam(name = "lang", required = false) String lang) {
		log.info(LogHelper.start(getClass(), "deleteModuelById(id)"));
		
		deleteUseCase.softDeleteById(id);
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMessage = messageSource.getMessage("deleted.successfully", null, currentLocale);
		return ResponseEntity.ok(ResponseEasy.success(successMessage));
	}
	
}
