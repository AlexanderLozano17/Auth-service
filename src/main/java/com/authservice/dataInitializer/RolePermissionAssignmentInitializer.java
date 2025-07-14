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
public class RolePermissionAssignmentInitializer {

private static final Logger LOGGER = LoggerFactory.getLogger(RolePermissionAssignmentInitializer.class);
	
	private final RoleJpaRepository roleJpaRepository;
	private final PermissionJpaRepository permissionJpaRepository;
	private final RolePermissionJpaRepository rolePermissionJpaRepository;

	@Transactional
	public void rolePermissionRun() {
		LOGGER.info("Iniciando verificación e inicialización de asignaciones de Roles y Permisos...");

        try {
            List<RolePermissionEntity> newAssignments = getRolesWithPermission();
            rolePermissionJpaRepository.saveAll(newAssignments);
            LOGGER.info("Asignaciones de Roles y Permisos guardadas exitosamente: {} asignaciones creadas.", newAssignments.size());

        } catch (Exception e) {
            LOGGER.error("Error al inicializar las asignaciones de Roles y Permisos: {}", e.getMessage(), e);
        }
       		
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

        // Rol: WAITER (Mesero)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "WAITER",
                "AUTH:VIEW_PROFILE", "AUTH:UPDATE_PROFILE", "AUTH:CHANGE_PASSWORD",
                "MENU:VIEW_ALL_ITEMS",
                "ORDER:CREATE", "ORDER:UPDATE_STATUS", "ORDER:ADD_ITEM", "ORDER:REMOVE_ITEM", "ORDER:VIEW_OWN",
                "TABLE:VIEW_STATUS", "TABLE:ASSIGN", "TABLE:MARK_CLEAN",
                "BILLING:GENERATE_BILL", "BILLING:PROCESS_PAYMENT", "BILLING:SPLIT_BILL"
        );

        // Rol: CHEF (Cocinero)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "CHEF",
                "AUTH:VIEW_PROFILE", "AUTH:UPDATE_PROFILE", "AUTH:CHANGE_PASSWORD",
                "KDS:VIEW_ORDERS", "KDS:MARK_PREPARING", "KDS:MARK_READY",
                "INV:VIEW_STOCK" // Puede que necesite ver el stock de ingredientes
        );

        // Rol: CASHIER (Cajero)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "CASHIER",
                "AUTH:VIEW_PROFILE", "AUTH:UPDATE_PROFILE", "AUTH:CHANGE_PASSWORD",
                "BILLING:GENERATE_BILL", "BILLING:PROCESS_PAYMENT", "BILLING:SPLIT_BILL", "BILLING:VIEW_HISTORY",
                "ORDER:VIEW_ALL" // Puede que necesite ver todos los pedidos para facturar
        );

        // Rol: INVENTORY_STAFF (Personal de Inventario)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "INVENTORY_STAFF",
                "AUTH:VIEW_PROFILE", "AUTH:UPDATE_PROFILE", "AUTH:CHANGE_PASSWORD",
                "INV:VIEW_STOCK", "INV:ADD_STOCK", "INV:CONSUME_STOCK", "INV:ADJUST_STOCK"
        );

        // Rol: CUSTOMER (Cliente / Invitado)
        assignPermissionToRole(assignments, rolesByName, permissionsByName, "CUSTOMER",
                "AUTH:VIEW_PROFILE", "AUTH:UPDATE_PROFILE", "AUTH:CHANGE_PASSWORD",
                "MENU:VIEW_ALL_ITEMS",
                "RESERVE:CREATE", "RESERVE:VIEW_ALL", "RESERVE:UPDATE", "RESERVE:CANCEL",
                "ORDER:VIEW_OWN" // Ver sus propios pedidos
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
