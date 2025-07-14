package com.authservice.dataInitializer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.authservice.entities.ModuleEntity;
import com.authservice.repositories.ModuleJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModulesAuthDataInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModulesAuthDataInitializer.class);
	
	private final ModuleJpaRepository moduleJpaRepository;
	private final ModulesDataInitializer modulesOther;
	
	// Contador para generar IDs de roles (solo para el ejemplo, en DB sería auto-generado)
    private static long roleIdCounter = 1L;
	
	@Transactional
	public void modulesRun() {
		LOGGER.info("Iniciando verificación e inicialización de datos de Modulos...");

		if (moduleJpaRepository.count() == 0) {
			LOGGER.info("No se encontraron Modulos. Inicializando con datos por defecto.");

			try {
				List<ModuleEntity> listModules = listModules(roleIdCounter);

				Long count = (long) moduleJpaRepository.saveAll(listModules).size();
				if (count > 0) {
					LOGGER.info("Datos de Modulos inicializados exitosamente. Se insertaron {} Modulos.", listModules.size());
					
					//Ejecución de los demas modulos
					modulesOther.modulesRun(count+1);
				}				

			} catch (Exception e) {
				LOGGER.error("Error al inicializar los datos de Modulos: {}", e.getMessage(), e);
			}
		} else {
			LOGGER.info("Ya existen Modulos en la base de datos. No se requiere inicialización.");
		}
		LOGGER.info("Finalizada la verificación e inicialización de datos de Modulos.");
	}
	
	List<ModuleEntity> listModules(Long roleIdCounter) {
		
		// --- Módulos de Autenticación y Autorización ---

		// Módulo: AUTH_CORE
        ModuleEntity authCore = new ModuleEntity();
        authCore.setId(roleIdCounter++);
        authCore.setNameEs("CORE_AUTENTICACION");
        authCore.setNameEn("AUTH_CORE");
        authCore.setDescriptionEs("Contiene las funcionalidades esenciales de autenticación y gestión de sesiones.");
        authCore.setDescriptionEn("Contains essential authentication and session management functionalities.");
        authCore.setPrefix("AUTH");
        authCore.setActive(true);

        // Módulo: USER_MANAGEMENT
        ModuleEntity userManagement = new ModuleEntity();
        userManagement.setId(roleIdCounter++);
        userManagement.setNameEs("GESTION_USUARIOS");
        userManagement.setNameEn("USER_MANAGEMENT");
        userManagement.setDescriptionEs("Gestiona todas las operaciones relacionadas con la administración de cuentas de usuario.");
        userManagement.setDescriptionEn("Manages all operations related to user account administration.");
        userManagement.setPrefix("USER");
        userManagement.setActive(true);

        // Módulo: ROLE_MANAGEMENT
        ModuleEntity roleManagement = new ModuleEntity();
        roleManagement.setId(roleIdCounter++);
        roleManagement.setNameEs("GESTION_ROLES");
        roleManagement.setNameEn("ROLE_MANAGEMENT");
        roleManagement.setDescriptionEs("Gestiona la creación, asignación y administración de roles.");
        roleManagement.setDescriptionEn("Manages the creation, assignment, and administration of roles.");
        roleManagement.setPrefix("ROLE");
        roleManagement.setActive(true);

        // Módulo: PERMISSION_MANAGEMENT
        ModuleEntity permissionManagement = new ModuleEntity();
        permissionManagement.setId(roleIdCounter++);
        permissionManagement.setNameEs("GESTION_PERMISOS");
        permissionManagement.setNameEn("PERMISSION_MANAGEMENT");
        permissionManagement.setDescriptionEs("Administra la creación, visualización y asignación de permisos individuales.");
        permissionManagement.setDescriptionEn("Administers the creation, viewing, and assignment of individual permissions.");
        permissionManagement.setPrefix("PERM");
        permissionManagement.setActive(true);

        // Módulo: AUDIT_LOGS
        ModuleEntity auditLogs = new ModuleEntity();
        auditLogs.setId(roleIdCounter++);
        auditLogs.setNameEs("REGISTROS_AUDITORIA");
        auditLogs.setNameEn("AUDIT_LOGS");
        auditLogs.setDescriptionEs("Permite la visualización de registros de auditoría y actividades del sistema.");
        auditLogs.setDescriptionEn("Allows viewing of system audit logs and activities.");
        auditLogs.setPrefix("AUDIT");
        auditLogs.setActive(true);

        // Módulo: SETTINGS
        ModuleEntity settings = new ModuleEntity();
        settings.setId(roleIdCounter++);
        settings.setNameEs("CONFIGURACION_SISTEMA");
        settings.setNameEn("SETTINGS");
        settings.setDescriptionEs("Controla el acceso a configuraciones globales del sistema de autenticación.");
        settings.setDescriptionEn("Controls access to global authentication system settings.");
        settings.setPrefix("CONFIG");
        settings.setActive(true);
        
        // Retorna todas las 6 instancias en una lista inmutable
        return List.of(
            authCore,
            userManagement,
            roleManagement,
            permissionManagement,
            auditLogs,
            settings
        );
	}

}
