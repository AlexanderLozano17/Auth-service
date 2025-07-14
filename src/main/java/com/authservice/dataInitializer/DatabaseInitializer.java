package com.authservice.dataInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);
	
	
	private final UsersDataInitializer users; // Crea usuarios
	private final RolesAuthDataInitializer roles; // Crea roles
	private final ModulesAuthDataInitializer modules; // Crea modulos
	private final PermissionAuthDataInitializer permission; // Crea permisos
	private final UserRoleAssignmentInitializer userRoles; // Crea la asignación de los roles a los usuarios
	private final RolePermissionAuthAssignmentInitializer rolePermission; // Crea la asignación de los permisos a los roles
	
	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("init" + DatabaseInitializer.class.getName() + " | Insertando datos de pruebas");
		
		users.usersRun();
		roles.rolesRun();
		userRoles.userRolesRun();
		modules.modulesRun();
		permission.permissionRun();
		rolePermission.rolePermissionRun();

        LOGGER.info(DatabaseInitializer.class.getName() + " | fin inserción datos de pruebas");	
	}

}
