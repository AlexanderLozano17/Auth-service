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
public class RolesDataInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RolesDataInitializer.class);
	
	private final RoleJpaRepository roleJpaRepository;

	@Transactional
	public void rolesRun(Long roleIdCounter) {
		LOGGER.info("Iniciando verificación e inicialización de datos de Permission...");
	
		try {
			List<RoleEntity> listRoles = listRoles(roleIdCounter);
			
			roleJpaRepository.saveAll(listRoles);
			LOGGER.info("Datos de Roles inicializados exitosamente. Se insertaron {} Roles.", listRoles.size());

		} catch (Exception e) {
			LOGGER.error("Error al inicializar los datos de Roles: {}", e.getMessage(), e);
		}		
	}
	
    public static List<RoleEntity> listRoles(Long roleIdCounter) {
    	
    	// 1. Rol: WAITER (Mesero)
        // Encargado de tomar pedidos, gestionar mesas y procesar pagos.
        RoleEntity waiter = new RoleEntity();
        waiter.setId(roleIdCounter++);
        waiter.setName("WAITER");
        waiter.setDescriptionEs("Toma pedidos de clientes, gestiona el estado de las mesas y procesa pagos.");
        waiter.setDescriptionEn("Takes customer orders, manages table status, and processes payments.");
        waiter.setActive(true);
        // Los permisos específicos para este rol se asignarían aquí o en un script de seeding.


        // 2. Rol: CHEF (Cocinero)
        // Responsable de la preparación de alimentos y de marcar el estado de los pedidos en cocina.
        RoleEntity chef = new RoleEntity();
        chef.setId(roleIdCounter++);
        chef.setName("CHEF");
        chef.setDescriptionEs("Prepara los platos del menú, visualiza pedidos de cocina y marca su estado (en preparación, listo).");
        chef.setDescriptionEn("Prepares menu dishes, views kitchen orders, and marks their status (in preparation, ready).");
        chef.setActive(true);

        // 3. Rol: CASHIER (Cajero)
        // Encargado de la facturación y el procesamiento de pagos.
        RoleEntity cashier = new RoleEntity();
        cashier.setId(roleIdCounter++);
        cashier.setName("CASHIER");
        cashier.setDescriptionEs("Gestiona la generación de facturas y el procesamiento de pagos en diferentes modalidades.");
        cashier.setDescriptionEn("Manages bill generation and payment processing in various modalities.");
        cashier.setActive(true);

        // 4. Rol: INVENTORY_STAFF (Personal de Inventario)
        // Encargado de la gestión física del inventario.
        RoleEntity inventoryStaff = new RoleEntity();
        inventoryStaff.setId(roleIdCounter++);
        inventoryStaff.setName("INVENTORY_STAFF");
        inventoryStaff.setDescriptionEs("Realiza el seguimiento del inventario, registra entradas y salidas, y gestiona los suministros.");
        inventoryStaff.setDescriptionEn("Tracks inventory, records inflows and outflows, and manages supplies.");
        inventoryStaff.setActive(true);

        // 5. Rol: CUSTOMER (Cliente / Invitado)
        // Si tu aplicación incluye un portal de cliente o un sistema de reservas para usuarios finales.
        RoleEntity customer = new RoleEntity();
        customer.setId(roleIdCounter++);
        customer.setName("CUSTOMER");
        customer.setDescriptionEs("Usuario final con acceso para ver el menú, realizar reservas y ver su historial de pedidos.");
        customer.setDescriptionEn("End user with access to view the menu, make reservations, and view their order history.");
        customer.setActive(true);

		return List.of(waiter, chef, cashier, inventoryStaff, customer);
    }
}
