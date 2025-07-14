package com.authservice.dataInitializer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.authservice.entities.RoleEntity;
import com.authservice.repositories.RoleJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RolesAuthDataInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RolesAuthDataInitializer.class);
	
	private final RoleJpaRepository roleJpaRepository;
	private final RolesDataInitializer roles;
	
	// Contador para generar IDs de roles (solo para el ejemplo, en DB sería auto-generado)
    private static long roleIdCounter = 1L;

	@Transactional
	public void rolesRun() {
		LOGGER.info("Iniciando verificación e inicialización de datos de Permission...");

		if (roleJpaRepository.count() == 0) {
			LOGGER.info("No se encontraron Roles. Inicializando con datos por defecto.");

			try {
				List<RoleEntity> listRoles = listRoles();
				
				Long count = (long) roleJpaRepository.saveAll(listRoles).size();				
				if (count > 0) {
					LOGGER.info("Datos de Roles inicializados exitosamente. Se insertaron {} Roles con permisos elevados.", listRoles.size());
				
					// Ejecución de los demás roles
					roles.rolesRun(count+1);
				}

			} catch (Exception e) {
				LOGGER.error("Error al inicializar los datos de Roles: {}", e.getMessage(), e);
			}
		} else {
			LOGGER.info("Ya existen Roles en la base de datos. No se requiere inicialización.");
		}
		LOGGER.info("Finalizada la verificación e inicialización de datos de Roles.");
		
	}

    public static List<RoleEntity> listRoles() {
    	
    	// 1. Rol: SUPER_ADMIN (Propietario / Administrador Total del Sistema)
        // Este rol debería tener acceso a TODO, incluyendo la gestión de otros administradores.
        RoleEntity superAdmin = new RoleEntity();
        superAdmin.setId(roleIdCounter++);
        superAdmin.setName("SUPER_ADMIN");
        superAdmin.setDescriptionEs("Acceso total y sin restricciones a todas las funcionalidades del sistema, incluyendo la gestión de usuarios, roles y permisos a nivel global.");
        superAdmin.setDescriptionEn("Full and unrestricted access to all system functionalities, including global user, role, and permission management.");
        superAdmin.setActive(true);
        // En una aplicación real, a este rol se le asignarían *todos* los permisos disponibles.
        // superAdmin.setPermissions(new HashSet<>(PermissionProvider.getAllPermissions())); // Si tienes un PermissionProvider
        

        // 2. Rol: ADMIN (Administrador General)
        // Este rol tiene amplios permisos para gestionar la aplicación, pero quizás no a nivel de super-administrador (ej. no puede eliminar super-admins).
        RoleEntity admin = new RoleEntity();
        admin.setId(roleIdCounter++);
        admin.setName("ADMIN");
        admin.setDescriptionEs("Acceso administrativo completo a las funcionalidades operativas del sistema, como la gestión de usuarios, menú, pedidos e inventario.");
        admin.setDescriptionEn("Full administrative access to operational system functionalities, such as user management, menu, orders, and inventory.");
        admin.setActive(true);

        // 3. Rol: AUDITOR (Rol de Solo Lectura para Auditoría)
        // Este rol tiene acceso de solo lectura a registros y datos sensibles para fines de auditoría y cumplimiento.
        RoleEntity auditor = new RoleEntity();
        auditor.setId(roleIdCounter++);
        auditor.setName("AUDITOR");
        auditor.setDescriptionEs("Acceso de solo lectura a registros de auditoría, informes y datos sensibles para fines de cumplimiento y revisión.");
        auditor.setDescriptionEn("Read-only access to audit logs, reports, and sensitive data for compliance and review purposes.");
        auditor.setActive(true);

        // 4. Rol: SECURITY_ADMIN (Administrador de Seguridad)
        // Un rol más especializado que se enfoca en la gestión de seguridad, políticas de contraseña, MFA, etc.
        RoleEntity securityAdmin = new RoleEntity();
        securityAdmin.setId(roleIdCounter++);
        securityAdmin.setName("SECURITY_ADMIN");
        securityAdmin.setDescriptionEs("Administra la configuración de seguridad del sistema, incluyendo políticas de contraseña, MFA y gestión de claves de API.");
        securityAdmin.setDescriptionEn("Manages system security configurations, including password policies, MFA, and API key management.");
        securityAdmin.setActive(true);
        
        // 5. Rol: MANAGER (Gerente)
        RoleEntity maneger = new RoleEntity();
        maneger.setId(roleIdCounter++);
        maneger.setName("MANAGER");
        maneger.setDescriptionEs("Responsable de la supervisión y coordinación de las operaciones diarias del restaurante.");
        maneger.setDescriptionEn("Responsible for overseeing and coordinating the daily operations of the restaurant.");
        maneger.setActive(true);
                
        
		return List.of(superAdmin, admin, auditor, securityAdmin, maneger);
    }
}
