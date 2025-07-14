package com.authservice.dataInitializer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.authservice.entities.ModuleEntity;
import com.authservice.entities.PermissionEntity;
import com.authservice.repositories.ModuleJpaRepository;
import com.authservice.repositories.PermissionJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PermissionAuthDataInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionAuthDataInitializer.class);
	
	private final PermissionJpaRepository permissionJpaRepository;
	private final ModuleJpaRepository moduleJpaRepository;
	private final PermissionDataInitializer permission;
	
	@Transactional
	public void permissionRun() {
		LOGGER.info("Iniciando verificación e inicialización de datos de Permission...");

		if (permissionJpaRepository.count() == 0) {
			LOGGER.info("No se encontraron Permission. Inicializando con datos por defecto.");

			try {
				List<PermissionEntity> listPermission = listPermission();
				
				Long count = (long) permissionJpaRepository.saveAll(listPermission).size();

				if (count > 0) {
					LOGGER.info("Datos de Permission inicializados exitosamente. Se insertaron {} Permission.", listPermission.size());
					
					// Creación de otros permisos no administrativos
					permission.permissionRun(count+1);				
				}
				
			} catch (Exception e) {
				LOGGER.error("Error al inicializar los datos de Permission: {}", e.getMessage(), e);
			}
		} else {
			LOGGER.info("Ya existen Permission en la base de datos. No se requiere inicialización.");
		}
		LOGGER.info("Finalizada la verificación e inicialización de datos de Permission.");
	}
	
	
	public List<PermissionEntity> listPermission() {
        List<PermissionEntity> permissions = new ArrayList<>();
        
        List<ModuleEntity> listModules = moduleJpaRepository.findAll();

        // Itera sobre cada módulo para crear sus permisos asociados
        for (ModuleEntity module : listModules) {
            String prefix = module.getPrefix();

            switch (prefix) {
                case "AUTH":
                    permissions.add(createPermission(module, "LOGIN", "Permiso para iniciar sesión.", "Permission to log in."));
                    permissions.add(createPermission(module, "LOGOUT", "Permiso para cerrar sesión.", "Permission to log out."));
                    permissions.add(createPermission(module, "REFRESH_TOKEN", "Permiso para refrescar el token de acceso.", "Permission to refresh access token."));
                    permissions.add(createPermission(module, "VIEW_PROFILE", "Permiso para ver el propio perfil de usuario.", "Permission to view own user profile."));
                    permissions.add(createPermission(module, "UPDATE_PROFILE", "Permiso para actualizar el propio perfil de usuario.", "Permission to update own user profile."));
                    permissions.add(createPermission(module, "CHANGE_PASSWORD", "Permiso para cambiar la propia contraseña.", "Permission to change own password."));
                    break;
                case "USER":
                    permissions.add(createPermission(module, "CREATE", "Permiso para crear nuevas cuentas de usuario.", "Permission to create new user accounts."));
                    permissions.add(createPermission(module, "VIEW_ALL", "Permiso para listar y ver detalles de todos los usuarios.", "Permission to list and view details of all users."));
                    permissions.add(createPermission(module, "UPDATE", "Permiso para editar información de usuarios existentes.", "Permission to edit existing user information."));
                    permissions.add(createPermission(module, "DELETE", "Permiso para eliminar cuentas de usuario.", "Permission to delete user accounts."));
                    permissions.add(createPermission(module, "ACTIVATE", "Permiso para activar una cuenta de usuario.", "Permission to activate a user account."));
                    permissions.add(createPermission(module, "DEACTIVATE", "Permiso para desactivar (bloquear) una cuenta de usuario.", "Permission to deactivate (block) a user account."));
                    permissions.add(createPermission(module, "RESET_PASSWORD", "Permiso para restablecer la contraseña de otros usuarios.", "Permission to reset other users' passwords."));
                    break;
                case "ROLE":
                    permissions.add(createPermission(module, "CREATE", "Permiso para crear nuevos roles.", "Permission to create new roles."));
                    permissions.add(createPermission(module, "VIEW_ALL", "Permiso para listar y ver detalles de todos los roles.", "Permission to list and view details of all roles."));
                    permissions.add(createPermission(module, "UPDATE", "Permiso para editar la información de roles existentes.", "Permission to edit existing role information."));
                    permissions.add(createPermission(module, "DELETE", "Permiso para eliminar roles.", "Permission to delete roles."));
                    permissions.add(createPermission(module, "ASSIGN_TO_USER", "Permiso para asignar un rol a un usuario.", "Permission to assign a role to a user."));
                    permissions.add(createPermission(module, "REVOKE_FROM_USER", "Permiso para quitar un rol a un usuario.", "Permission to revoke a role from a user."));
                    break;
                case "PERM":
                    permissions.add(createPermission(module, "CREATE", "Permiso para crear nuevos permisos.", "Permission to create new permissions."));
                    permissions.add(createPermission(module, "VIEW_ALL", "Permiso para listar y ver detalles de todos los permisos.", "Permission to list and view details of all permissions."));
                    permissions.add(createPermission(module, "UPDATE", "Permiso para editar permisos existentes.", "Permission to edit existing permissions."));
                    permissions.add(createPermission(module, "DELETE", "Permiso para eliminar permisos.", "Permission to delete permissions."));
                    permissions.add(createPermission(module, "ASSIGN_TO_ROLE", "Permiso para asignar un permiso a un rol.", "Permission to assign a permission to a role."));
                    permissions.add(createPermission(module, "REVOKE_FROM_ROLE", "Permiso para quitar un permiso de un rol.", "Permission to revoke a permission from a role."));
                    break;
                case "AUDIT":
                    permissions.add(createPermission(module, "VIEW_LOGS", "Permiso para acceder y ver los registros de auditoría.", "Permission to access and view audit logs."));
                    permissions.add(createPermission(module, "EXPORT_LOGS", "Permiso para exportar los registros de auditoría.", "Permission to export audit logs."));
                    break;
                case "CONFIG":
                    permissions.add(createPermission(module, "VIEW_SYSTEM", "Permiso para ver las configuraciones del sistema.", "Permission to view system settings."));
                    permissions.add(createPermission(module, "MANAGE_PASSWORD_POLICY", "Permiso para configurar la complejidad o expiración de contraseñas.", "Permission to configure password complexity or expiration."));
                    permissions.add(createPermission(module, "MANAGE_MFA", "Permiso para configurar opciones de autenticación multifactor (MFA).", "Permission to configure multi-factor authentication (MFA) options."));
                    break;
               
                default:
                    System.out.println("Módulo con prefijo desconocido: " + prefix);
                    break;
            }
        }
        return permissions;
	}
	
	// Contador para generar IDs de permisos (solo para el ejemplo, en DB sería auto-generado)
    private static long permissionIdCounter = 1000L;
	
    private static PermissionEntity createPermission(ModuleEntity module, String action, String descEs, String descEn) {
    	PermissionEntity permission = new PermissionEntity();
        permission.setId(permissionIdCounter++); // Asigna un ID único de ejemplo
        permission.setName(module.getPrefix() + ":" + action); // Formato PREFIJO:accion
        permission.setDescriptionEs(descEs);
        permission.setDescriptionEn(descEn);
        permission.setModule(module); // Asigna el módulo al permiso
        return permission;
    }
}
