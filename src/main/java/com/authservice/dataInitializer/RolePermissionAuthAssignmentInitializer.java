package com.authservice.dataInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.authservice.entities.PermissionEntity;
import com.authservice.entities.RoleEntity;
import com.authservice.entities.RolePermissionEntity;
import com.authservice.repositories.PermissionJpaRepository;
import com.authservice.repositories.RoleJpaRepository;
import com.authservice.repositories.RolePermissionJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RolePermissionAuthAssignmentInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RolePermissionAuthAssignmentInitializer.class);

	private final RoleJpaRepository roleJpaRepository;
	private final PermissionJpaRepository permissionJpaRepository;
	private final RolePermissionJpaRepository rolePermissionJpaRepository;
	private final RolePermissionAssignmentInitializer rolePermissionAssignmentInitializer;

	@Transactional
	public void rolePermissionRun() {
		LOGGER.info("Iniciando verificación e inicialización de asignaciones de Roles y Permisos...");

        // Verifica si ya existen asignaciones de roles y permisos
        if (rolePermissionJpaRepository.count() == 0) { 
            LOGGER.info("No se encontraron asignaciones de Roles y Permisos. Inicializando con datos por defecto.");

            if (roleJpaRepository.count() == 0 || permissionJpaRepository.count() == 0) {
                LOGGER.error("No se pueden asignar permisos a roles: Faltan roles o permisos en la base de datos. Asegúrate de que los inicializadores de Roles y Permisos se hayan ejecutado primero.");
                return;
            }

            try {
                List<RolePermissionEntity> newAssignments = getRolesWithPermission();
                if(rolePermissionJpaRepository.saveAll(newAssignments).size() > 0 ) {
                	LOGGER.info("Asignaciones de Roles y Permisos guardadas exitosamente: {} asignaciones creadas.", newAssignments.size());
                
                	rolePermissionAssignmentInitializer.rolePermissionRun();
                }

            } catch (Exception e) {
                LOGGER.error("Error al inicializar las asignaciones de Roles y Permisos: {}", e.getMessage(), e);
            }
        } else {
            LOGGER.info("Ya existen asignaciones de Roles y Permisos en la base de datos. No se requiere inicialización.");
        }
        LOGGER.info("Finalizada la verificación e inicialización de asignaciones de Roles y Permisos.");
    }
	
	private List<RolePermissionEntity> getRolesWithPermission() {
		List<RolePermissionEntity> assignments = new ArrayList<>();

        // --- 1. Obtener todos los roles y permisos de la base de datos ---
        List<RoleEntity> allRoles = roleJpaRepository.findAll();
        List<PermissionEntity> allPermissions = permissionJpaRepository.findAll();

        // Crear mapas para buscar rápidamente roles y permisos por nombre
        Map<String, RoleEntity> rolesByName = allRoles.stream()
                .collect(Collectors.toMap(RoleEntity::getName, role -> role));
        
        Map<String, PermissionEntity> permissionsByName = allPermissions.stream()
                .collect(Collectors.toMap(PermissionEntity::getName, permission -> permission));

        // --- 2. Definir y asignar permisos para cada rol ---

        // Rol: SUPER_ADMIN (Propietario / Administrador Total del Sistema)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "SUPER_ADMIN",
                // Asigna *todos* los permisos existentes al SUPER_ADMIN
                permissionsByName.keySet().toArray(new String[0])
        );

        // Rol: ADMIN (Administrador General)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "ADMIN",
                "AUTH:LOGIN", "AUTH:LOGOUT", "AUTH:REFRESH_TOKEN", "AUTH:VIEW_PROFILE", "AUTH:UPDATE_PROFILE", "AUTH:CHANGE_PASSWORD",
                "USER:CREATE", "USER:VIEW_ALL", "USER:UPDATE", "USER:DELETE", "USER:ACTIVATE", "USER:DEACTIVATE", "USER:RESET_PASSWORD",
                "ROLE:CREATE", "ROLE:VIEW_ALL", "ROLE:UPDATE", "ROLE:DELETE", "ROLE:ASSIGN_TO_USER", "ROLE:REVOKE_FROM_USER",
                "PERM:VIEW_ALL",
                "AUDIT:VIEW_LOGS", "AUDIT:EXPORT_LOGS",
                "CONFIG:VIEW_SYSTEM", "CONFIG:MANAGE_PASSWORD_POLICY", "CONFIG:MANAGE_MFA",
                "MENU:CREATE_ITEM", "MENU:VIEW_ALL_ITEMS", "MENU:UPDATE_ITEM", "MENU:DELETE_ITEM", "MENU:MANAGE_CATEGORIES",
                "ORDER:CREATE", "ORDER:VIEW_ALL", "ORDER:UPDATE_STATUS", "ORDER:ADD_ITEM", "ORDER:REMOVE_ITEM", "ORDER:APPLY_DISCOUNT", "ORDER:CANCEL",
                "KDS:VIEW_ORDERS", "KDS:MARK_PREPARING", "KDS:MARK_READY",
                "TABLE:VIEW_STATUS", "TABLE:ASSIGN", "TABLE:MARK_CLEAN", "TABLE:MERGE",
                "INV:VIEW_STOCK", "INV:ADD_STOCK", "INV:CONSUME_STOCK", "INV:ADJUST_STOCK", "INV:MANAGE_SUPPLIERS", "INV:CREATE_PO",
                "BILLING:GENERATE_BILL", "BILLING:PROCESS_PAYMENT", "BILLING:SPLIT_BILL", "BILLING:ISSUE_REFUND", "BILLING:VIEW_HISTORY",
                "REPORT:VIEW_SALES", "REPORT:VIEW_INVENTORY", "REPORT:VIEW_EMPLOYEE_PERFORMANCE", "REPORT:EXPORT",
                "EMPLOYEE:CREATE", "EMPLOYEE:VIEW_ALL", "EMPLOYEE:UPDATE", "EMPLOYEE:DELETE", "EMPLOYEE:MANAGE_SCHEDULES",
                "RESERVE:CREATE", "RESERVE:VIEW_ALL", "RESERVE:UPDATE", "RESERVE:CANCEL"
        );

        // Rol: AUDITOR (Rol de Solo Lectura para Auditoría)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "AUDITOR",
                "AUDIT:VIEW_LOGS", "AUDIT:EXPORT_LOGS",
                "USER:VIEW_ALL",
                "ROLE:VIEW_ALL",
                "PERM:VIEW_ALL",
                "MENU:VIEW_ALL_ITEMS",
                "ORDER:VIEW_ALL",
                "INV:VIEW_STOCK",
                "BILLING:VIEW_HISTORY",
                "REPORT:VIEW_SALES", "REPORT:VIEW_INVENTORY", "REPORT:VIEW_EMPLOYEE_PERFORMANCE", "REPORT:EXPORT"
        );

        // Rol: SECURITY_ADMIN (Administrador de Seguridad)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "SECURITY_ADMIN",
                "AUTH:VIEW_PROFILE", "AUTH:UPDATE_PROFILE", "AUTH:CHANGE_PASSWORD",
                "USER:CREATE", "USER:VIEW_ALL", "USER:UPDATE", "USER:ACTIVATE", "USER:DEACTIVATE", "USER:RESET_PASSWORD",
                "ROLE:CREATE", "ROLE:VIEW_ALL", "ROLE:UPDATE", "ROLE:ASSIGN_TO_USER", "ROLE:REVOKE_FROM_USER",
                "PERM:CREATE", "PERM:VIEW_ALL", "PERM:UPDATE", "PERM:DELETE", "PERM:ASSIGN_TO_ROLE", "PERM:REVOKE_FROM_ROLE",
                "CONFIG:VIEW_SYSTEM", "CONFIG:MANAGE_PASSWORD_POLICY", "CONFIG:MANAGE_MFA"
        );

        // Rol: MANAGER (Gerente)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "MANAGER",
                "AUTH:VIEW_PROFILE", "AUTH:UPDATE_PROFILE", "AUTH:CHANGE_PASSWORD",
                "USER:VIEW_ALL", "USER:UPDATE", "USER:ACTIVATE", "USER:DEACTIVATE", "USER:RESET_PASSWORD", // Gestión de empleados
                "ROLE:VIEW_ALL", "ROLE:ASSIGN_TO_USER", "ROLE:REVOKE_FROM_USER", // Asignación de roles a empleados
                "MENU:CREATE_ITEM", "MENU:VIEW_ALL_ITEMS", "MENU:UPDATE_ITEM", "MENU:DELETE_ITEM", "MENU:MANAGE_CATEGORIES",
                "ORDER:CREATE", "ORDER:VIEW_ALL", "ORDER:UPDATE_STATUS", "ORDER:ADD_ITEM", "ORDER:REMOVE_ITEM", "ORDER:APPLY_DISCOUNT", "ORDER:CANCEL",
                "KDS:VIEW_ORDERS", "KDS:MARK_PREPARING", "KDS:MARK_READY",
                "TABLE:VIEW_STATUS", "TABLE:ASSIGN", "TABLE:MARK_CLEAN", "TABLE:MERGE",
                "INV:VIEW_STOCK", "INV:ADD_STOCK", "INV:CONSUME_STOCK", "INV:ADJUST_STOCK", "INV:MANAGE_SUPPLIERS", "INV:CREATE_PO",
                "BILLING:GENERATE_BILL", "BILLING:PROCESS_PAYMENT", "BILLING:SPLIT_BILL", "BILLING:ISSUE_REFUND", "BILLING:VIEW_HISTORY",
                "REPORT:VIEW_SALES", "REPORT:VIEW_INVENTORY", "REPORT:VIEW_EMPLOYEE_PERFORMANCE", "REPORT:EXPORT",
                "EMPLOYEE:CREATE", "EMPLOYEE:VIEW_ALL", "EMPLOYEE:UPDATE", "EMPLOYEE:DELETE", "EMPLOYEE:MANAGE_SCHEDULES",
                "RESERVE:CREATE", "RESERVE:VIEW_ALL", "RESERVE:UPDATE", "RESERVE:CANCEL"
        );

        return assignments;
    }

	/**
	 * Método auxiliar para asignar permisos a un rol
	 * 
	 * @param assignmentsList
	 * @param rolesMap
	 * @param permissionsMap
	 * @param roleName
	 * @param permissionNames
	 */
    private void assignPermissionToRole( List<RolePermissionEntity> assignmentsList, Map<String, RoleEntity> rolesMap,  Map<String, PermissionEntity> permissionsMap,
    		String roleName, String... permissionNames) {

	    RoleEntity role = rolesMap.get(roleName);
	    if (role == null) {
	        LOGGER.warn("Rol '{}' no encontrado en la base de datos. No se asignarán permisos.", roleName);
	        return;
	    }

	    for (String permName : permissionNames) {
	        PermissionEntity permission = permissionsMap.get(permName);
	        if (permission == null) {
	            LOGGER.warn("Permiso '{}' no encontrado en la base de datos para el rol '{}'. No se asignará.", permName, roleName);
	            continue;
	        }
	
	        // Crea la entidad de unión
	        RolePermissionEntity rolePermission = new RolePermissionEntity(role, permission);
	        assignmentsList.add(rolePermission);
	    }
    }
}
