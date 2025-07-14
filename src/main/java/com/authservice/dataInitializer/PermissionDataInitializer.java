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
public class PermissionDataInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionDataInitializer.class);
	
	private final PermissionJpaRepository permissionJpaRepository;
	private final ModuleJpaRepository moduleJpaRepository;

	@Transactional
	public void permissionRun(Long permissionIdCounter) {
		LOGGER.info("Iniciando verificación e inicialización de datos de Permission...");

		try {
			List<PermissionEntity> listPermission = listPermission(permissionIdCounter);
			
			permissionJpaRepository.saveAll(listPermission);
			LOGGER.info("Datos de Permission inicializados exitosamente. Se insertaron {} Permission.", listPermission.size());

		} catch (Exception e) {
			LOGGER.error("Error al inicializar los datos de Permission: {}", e.getMessage(), e);
		}
	}
	
	
	public List<PermissionEntity> listPermission(Long permissionIdCounter) {
		
        List<PermissionEntity> permissions = new ArrayList<>();
        
        List<ModuleEntity> listModules = moduleJpaRepository.findAll();

        // Itera sobre cada módulo para crear sus permisos asociados
        for (ModuleEntity module : listModules) {
            String prefix = module.getPrefix();

            switch (prefix) {
	            case "MENU":
	                permissions.add(createPermission(module, "CREATE_ITEM", "Crear un nuevo plato o bebida en el menú.", "Create a new dish or drink on the menu.", permissionIdCounter++));
	                permissions.add(createPermission(module, "VIEW_ALL_ITEMS", "Ver todos los elementos del menú.", "View all menu items.", permissionIdCounter++));
	                permissions.add(createPermission(module, "UPDATE_ITEM", "Editar detalles de un elemento del menú (precio, descripción).", "Edit menu item details (price, description).", permissionIdCounter++));
	                permissions.add(createPermission(module, "DELETE_ITEM", "Eliminar un elemento del menú.", "Delete a menu item.", permissionIdCounter++));
	                permissions.add(createPermission(module, "MANAGE_CATEGORIES", "Crear, editar o eliminar categorías del menú.", "Create, edit, or delete menu categories.", permissionIdCounter++));
	                break;
	            case "ORDER":
	                permissions.add(createPermission(module, "CREATE", "Tomar un nuevo pedido.", "Take a new order.", permissionIdCounter++));
	                permissions.add(createPermission(module, "VIEW_OWN", "Ver los pedidos creados por el propio usuario (mesero).", "View orders created by the user (waiter).", permissionIdCounter++));
	                permissions.add(createPermission(module, "VIEW_ALL", "Ver todos los pedidos activos.", "View all active orders.", permissionIdCounter++));
	                permissions.add(createPermission(module, "UPDATE_STATUS", "Cambiar el estado de un pedido (ej. de 'pendiente' a 'en preparación').", "Change the status of an order (e.g., from 'pending' to 'in preparation').", permissionIdCounter++));
	                permissions.add(createPermission(module, "ADD_ITEM", "Añadir un plato a un pedido existente.", "Add a dish to an existing order.", permissionIdCounter++));
	                permissions.add(createPermission(module, "REMOVE_ITEM", "Quitar un plato de un pedido.", "Remove a dish from an order.", permissionIdCounter++));
	                permissions.add(createPermission(module, "APPLY_DISCOUNT", "Aplicar descuentos a un pedido.", "Apply discounts to an order.", permissionIdCounter++));
	                permissions.add(createPermission(module, "CANCEL", "Cancelar un pedido.", "Cancel an order.", permissionIdCounter++));
	                break;
	            case "KDS":
	                permissions.add(createPermission(module, "VIEW_ORDERS", "Ver los pedidos en la pantalla de cocina.", "View orders on the kitchen display screen.", permissionIdCounter++));
	                permissions.add(createPermission(module, "MARK_PREPARING", "Marcar un pedido como 'en preparación'.", "Mark an order as 'in preparation'.", permissionIdCounter++));
	                permissions.add(createPermission(module, "MARK_READY", "Marcar un pedido como 'listo para servir'.", "Mark an order as 'ready to serve'.", permissionIdCounter++));
	                break;
	            case "TABLE":
	                permissions.add(createPermission(module, "VIEW_STATUS", "Ver el estado actual de todas las mesas.", "View the current status of all tables.", permissionIdCounter++));
	                permissions.add(createPermission(module, "ASSIGN", "Asignar una mesa a un cliente o pedido.", "Assign a table to a customer or order.", permissionIdCounter++));
	                permissions.add(createPermission(module, "MARK_CLEAN", "Marcar una mesa como limpia y disponible.", "Mark a table as clean and available.", permissionIdCounter++));
	                permissions.add(createPermission(module, "MERGE", "Unir varias mesas.", "Merge multiple tables.", permissionIdCounter++));
	                break;
	            case "INV":
	                permissions.add(createPermission(module, "VIEW_STOCK", "Ver el stock actual de todos los ingredientes.", "View the current stock of all ingredients.", permissionIdCounter++));
	                permissions.add(createPermission(module, "ADD_STOCK", "Registrar la entrada de nuevos suministros.", "Register the entry of new supplies.", permissionIdCounter++));
	                permissions.add(createPermission(module, "CONSUME_STOCK", "Registrar el consumo manual de ingredientes.", "Register manual ingredient consumption.", permissionIdCounter++));
	                permissions.add(createPermission(module, "ADJUST_STOCK", "Ajustar cantidades de inventario (por conteo físico, pérdidas).", "Adjust inventory quantities (by physical count, losses).", permissionIdCounter++));
	                permissions.add(createPermission(module, "MANAGE_SUPPLIERS", "Gestionar la información de los proveedores.", "Manage supplier information.", permissionIdCounter++));
	                permissions.add(createPermission(module, "CREATE_PO", "Generar órdenes de compra a proveedores.", "Generate purchase orders to suppliers.", permissionIdCounter++));
	                break;
	            case "BILLING":
	                permissions.add(createPermission(module, "GENERATE_BILL", "Generar la cuenta para una mesa/pedido.", "Generate the bill for a table/order.", permissionIdCounter++));
	                permissions.add(createPermission(module, "PROCESS_PAYMENT", "Registrar un pago (efectivo, tarjeta, etc.).", "Register a payment (cash, card, etc.).", permissionIdCounter++));
	                permissions.add(createPermission(module, "SPLIT_BILL", "Dividir la cuenta entre varios pagadores.", "Split the bill among multiple payers.", permissionIdCounter++));
	                permissions.add(createPermission(module, "ISSUE_REFUND", "Emitir reembolsos.", "Issue refunds.", permissionIdCounter++));
	                permissions.add(createPermission(module, "VIEW_HISTORY", "Ver el historial de transacciones de pago.", "View payment transaction history.", permissionIdCounter++));
	                break;
	            case "REPORT":
	                permissions.add(createPermission(module, "VIEW_SALES", "Ver informes de ventas diarios/semanales/mensuales.", "View daily/weekly/monthly sales reports.", permissionIdCounter++));
	                permissions.add(createPermission(module, "VIEW_INVENTORY", "Ver informes de stock y consumo de inventario.", "View inventory stock and consumption reports.", permissionIdCounter++));
	                permissions.add(createPermission(module, "VIEW_EMPLOYEE_PERFORMANCE", "Ver informes de rendimiento del personal.", "View staff performance reports.", permissionIdCounter++));
	                permissions.add(createPermission(module, "EXPORT", "Exportar informes a diferentes formatos.", "Export reports to different formats.", permissionIdCounter++));
	                break;
	            case "EMPLOYEE":
	                permissions.add(createPermission(module, "CREATE", "Añadir nuevos empleados.", "Add new employees.", permissionIdCounter++));
	                permissions.add(createPermission(module, "VIEW_ALL", "Ver la lista y detalles de todos los empleados.", "View the list and details of all employees.", permissionIdCounter++));
	                permissions.add(createPermission(module, "UPDATE", "Editar la información de un empleado.", "Edit an employee's information.", permissionIdCounter++));
	                permissions.add(createPermission(module, "DELETE", "Eliminar un empleado.", "Delete an employee.", permissionIdCounter++));
	                permissions.add(createPermission(module, "MANAGE_SCHEDULES", "Gestionar los horarios de los empleados.", "Manage employee schedules.", permissionIdCounter++));
	                break;
	            case "RESERVE":
	                permissions.add(createPermission(module, "CREATE", "Registrar una nueva reserva.", "Register a new reservation.", permissionIdCounter++));
	                permissions.add(createPermission(module, "VIEW_ALL", "Ver todas las reservas.", "View all reservations.", permissionIdCounter++));
	                permissions.add(createPermission(module, "UPDATE", "Modificar una reserva existente.", "Modify an existing reservation.", permissionIdCounter++));
	                permissions.add(createPermission(module, "CANCEL", "Cancelar una reserva.", "Cancel a reservation.", permissionIdCounter++));
	                break;
	            default:
	                System.out.println("Módulo con prefijo desconocido: " + prefix);
	                break;
            }
        }
        return permissions;
	}

    private static PermissionEntity createPermission(ModuleEntity module, String action, String descEs, String descEn, Long permissionIdCounter) {
    	PermissionEntity permission = new PermissionEntity();
        permission.setId(permissionIdCounter++); // Asigna un ID único de ejemplo
        permission.setName(module.getPrefix() + ":" + action); // Formato PREFIJO:ACCION
        permission.setDescriptionEs(descEs);
        permission.setDescriptionEn(descEn);
        permission.setModule(module); // Asigna el módulo al permiso
        return permission;
    }
}
