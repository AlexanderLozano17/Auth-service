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
public class ModulesDataInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModulesDataInitializer.class);
	
	private final ModuleJpaRepository moduleJpaRepository;
	
	@Transactional
	public void modulesRun(Long roleIdCounter) {
		
		LOGGER.info("Iniciando verificación e inicialización de datos de Modulos...");
			
		try {
			List<ModuleEntity> listModules = listModules(roleIdCounter);

			moduleJpaRepository.saveAll(listModules);
			LOGGER.info("Datos de Modulos inicializados exitosamente. Se insertaron {} Modulos.",
					listModules.size());

		} catch (Exception e) {
			LOGGER.error("Error al inicializar los datos de Modulos: {}", e.getMessage(), e);
		}		
	}
	
	List<ModuleEntity> listModules(Long roleIdCounter) {
		
		// --- Módulos de Gestión de Restaurantes ---

		// Módulo: MENU_MANAGEMENT
        ModuleEntity menuManagement = new ModuleEntity();
        menuManagement.setId(roleIdCounter++);
        menuManagement.setNameEs("GESTION_MENU");
        menuManagement.setNameEn("MENU_MANAGEMENT");
        menuManagement.setDescriptionEs("Gestiona la creación, edición y organización de los platos, bebidas y categorías del menú del restaurante.");
        menuManagement.setDescriptionEn("Manages the creation, editing, and organization of restaurant menu items, drinks, and categories.");
        menuManagement.setPrefix("MENU");
        menuManagement.setActive(true);

        // Módulo: ORDER_MANAGEMENT
        ModuleEntity orderManagement = new ModuleEntity();
        orderManagement.setId(roleIdCounter++);
        orderManagement.setNameEs("GESTION_PEDIDOS");
        orderManagement.setNameEn("ORDER_MANAGEMENT");
        orderManagement.setDescriptionEs("Maneja la toma de pedidos de los clientes, la asignación a mesas, el seguimiento del estado de los pedidos y la gestión del POS.");
        orderManagement.setDescriptionEn("Handles customer order taking, table assignment, order status tracking, and POS management.");
        orderManagement.setPrefix("ORDER");
        orderManagement.setActive(true);

        // Módulo: KITCHEN_DISPLAY_SYSTEM (KDS)
        ModuleEntity kds = new ModuleEntity();
        kds.setId(roleIdCounter++);
        kds.setNameEs("SISTEMA_PANTALLA_COCINA");
        kds.setNameEn("KITCHEN_DISPLAY_SYSTEM");
        kds.setDescriptionEs("Interfaz para el personal de cocina que muestra los pedidos entrantes y permite marcarlos como preparados o listos.");
        kds.setDescriptionEn("Interface for kitchen staff showing incoming orders and allowing them to be marked as prepared or ready.");
        kds.setPrefix("KDS");
        kds.setActive(true);

        // Módulo: TABLE_MANAGEMENT
        ModuleEntity tableManagement = new ModuleEntity();
        tableManagement.setId(roleIdCounter++);
        tableManagement.setNameEs("GESTION_MESAS");
        tableManagement.setNameEn("TABLE_MANAGEMENT");
        tableManagement.setDescriptionEs("Gestiona las mesas del restaurante, incluyendo su estado (ocupada, libre, limpia) y la asignación de clientes.");
        tableManagement.setDescriptionEn("Manages restaurant tables, including their status (occupied, free, clean) and customer assignment.");
        tableManagement.setPrefix("TABLE");
        tableManagement.setActive(true);

        // Módulo: INVENTORY_MANAGEMENT
        ModuleEntity inventoryManagement = new ModuleEntity();
        inventoryManagement.setId(roleIdCounter++);
        inventoryManagement.setNameEs("GESTION_INVENTARIO");
        inventoryManagement.setNameEn("INVENTORY_MANAGEMENT");
        inventoryManagement.setDescriptionEs("Rastrea las existencias de ingredientes y suministros, gestiona entradas y salidas, y emite alertas de bajo stock.");
        inventoryManagement.setDescriptionEn("Tracks ingredient and supply stock, manages inflows and outflows, and issues low stock alerts.");
        inventoryManagement.setPrefix("INV");
        inventoryManagement.setActive(true);

        // Módulo: PAYMENT_AND_BILLING
        ModuleEntity paymentAndBilling = new ModuleEntity();
        paymentAndBilling.setId(roleIdCounter++);
        paymentAndBilling.setNameEs("PAGOS_FACTURACION");
        paymentAndBilling.setNameEn("PAYMENT_AND_BILLING");
        paymentAndBilling.setDescriptionEs("Procesa pagos, genera facturas, divide cuentas y maneja diferentes métodos de pago.");
        paymentAndBilling.setDescriptionEn("Processes payments, generates invoices, splits bills, and handles different payment methods.");
        paymentAndBilling.setPrefix("BILLING");
        paymentAndBilling.setActive(true);

        // Módulo: REPORTS_AND_ANALYTICS
        ModuleEntity reportsAndAnalytics = new ModuleEntity();
        reportsAndAnalytics.setId(roleIdCounter++);
        reportsAndAnalytics.setNameEs("INFORMES_ANALITICAS");
        reportsAndAnalytics.setNameEn("REPORTS_AND_ANALYTICS");
        reportsAndAnalytics.setDescriptionEs("Genera informes sobre ventas, rendimiento de platos, inventario, horas de personal y otros datos clave para el análisis del negocio.");
        reportsAndAnalytics.setDescriptionEn("Generates reports on sales, dish performance, inventory, staff hours, and other key business analysis data.");
        reportsAndAnalytics.setPrefix("REPORT");
        reportsAndAnalytics.setActive(true);

        // Módulo: EMPLOYEE_MANAGEMENT
        ModuleEntity employeeManagement = new ModuleEntity();
        employeeManagement.setId(roleIdCounter++);
        employeeManagement.setNameEs("GESTION_EMPLEADOS");
        employeeManagement.setNameEn("EMPLOYEE_MANAGEMENT");
        employeeManagement.setDescriptionEs("Administración de la información de los empleados, sus roles (ej. mesero, cocinero) y horarios de trabajo.");
        employeeManagement.setDescriptionEn("Administration of employee information, their roles (e.g., waiter, cook), and work schedules.");
        employeeManagement.setPrefix("EMPLOYEE");
        employeeManagement.setActive(true);

        // Módulo: RESERVATIONS
        ModuleEntity reservations = new ModuleEntity();
        reservations.setId(roleIdCounter++);
        reservations.setNameEs("RESERVACIONES");
        reservations.setNameEn("RESERVATIONS"); // Corregido el nombre del método
        reservations.setDescriptionEs("Gestiona las reservas de mesas por parte de los clientes, incluyendo la disponibilidad, confirmación y cancelación.");
        reservations.setDescriptionEn("Manages table reservations by customers, including availability, confirmation, and cancellation.");
        reservations.setPrefix("RESERVE");
        reservations.setActive(true);

        return List.of(
            menuManagement,
            orderManagement,
            kds,
            tableManagement,
            inventoryManagement,
            paymentAndBilling,
            reportsAndAnalytics,
            employeeManagement,
            reservations
        );
	}
}
