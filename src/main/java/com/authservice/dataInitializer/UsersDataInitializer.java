package com.authservice.dataInitializer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.authservice.entities.UserEntity;
import com.authservice.repositories.UserJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsersDataInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersDataInitializer.class);
	
	private final UserJpaRepository userJpaRepository;
	
	@Transactional
	public void usersRun() {
		LOGGER.info("Iniciando verificación e inicialización de datos de Users...");

		if (userJpaRepository.count() == 0) {
			LOGGER.info("No se encontraron Users. Inicializando con datos por defecto.");

			try {
				List<UserEntity> listUsers = listUsers();

				userJpaRepository.saveAll(listUsers);
				LOGGER.info("Datos de Users inicializados exitosamente. Se insertaron {} Users.",
						listUsers.size());

			} catch (Exception e) {
				LOGGER.error("Error al inicializar los datos de Users: {}", e.getMessage(), e);
			}
		} else {
			LOGGER.info("Ya existen Users en la base de datos. No se requiere inicialización.");
		}
		LOGGER.info("Finalizada la verificación e inicialización de datos de Users.");
	}
	
	
	private List<UserEntity> listUsers() {
		
		long userIdCounter = 1L;
		
		List<UserEntity> users = new ArrayList<>();
		
		// --- User 1: Administrador activo ---
        UserEntity adminUser = new UserEntity(); // Usa el constructor por defecto
        adminUser.setUsername("admin_user");
        adminUser.setEmail("alexander.lozano@example.com");
        adminUser.setPassword("$2a$10$abcdefghijklmnopqrstuv.w.xyz0123456789"); // Simula hash de "adminPass123"
        adminUser.setFirstName("Alexander");
        adminUser.setLastName("Lozano");
        adminUser.setId(userIdCounter++);
        users.add(adminUser);

        // --- User 2: Usuario regular activo ---
        UserEntity regularUser = new UserEntity();
        regularUser.setUsername("johndoe");
        regularUser.setEmail("john.doe@example.com");
        regularUser.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz"); // Simula hash de "password123"
        regularUser.setFirstName("John");
        regularUser.setLastName("Doe");
        regularUser.setId(userIdCounter++);
        users.add(regularUser);

        // --- User 3: Usuario inactivo (quizás suspendido o sin verificar) ---
        UserEntity inactiveUser = new UserEntity();
        inactiveUser.setUsername("janedoe");
        inactiveUser.setEmail("jane.doe@example.com");
        inactiveUser.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz"); 
        inactiveUser.setFirstName("Jane");
        inactiveUser.setLastName("Doe");
        inactiveUser.setId(userIdCounter++);
        inactiveUser.setIsActive(false); // Explícitamente inactivo
        users.add(inactiveUser);

        // --- User 4: Nuevo usuario, listo para ser registrado ---
        UserEntity newUser = new UserEntity();
        newUser.setUsername("newuser2025");
        newUser.setEmail("dalas@example.com");
        newUser.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz"); 
        newUser.setFirstName("Dalas");
        newUser.setLastName("Morrish");
        newUser.setId(userIdCounter++);
        users.add(newUser);

        // --- User 5: Usuario de prueba ---
        UserEntity testUser = new UserEntity();
        testUser.setUsername("test_user_01");
        testUser.setEmail("freddy@example.com");
        testUser.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz"); 
        testUser.setFirstName("Freddy");
        testUser.setLastName("Rush");
        testUser.setId(userIdCounter++);
        users.add(testUser);

        // --- User 6: Gerente de Restaurante ---
        UserEntity managerUser = new UserEntity();
        managerUser.setUsername("manager_rest");
        managerUser.setEmail("manager@example.com");
        managerUser.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz");
        managerUser.setFirstName("Laura");
        managerUser.setLastName("García");
        managerUser.setId(userIdCounter++);
        users.add(managerUser);

        // --- User 7: Cajero Principal ---
        UserEntity cashierUser = new UserEntity();
        cashierUser.setUsername("main_cashier");
        cashierUser.setEmail("main.cashier@example.com");
        cashierUser.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz");
        cashierUser.setFirstName("Pedro");
        cashierUser.setLastName("Martínez");
        cashierUser.setId(userIdCounter++);
        users.add(cashierUser);

        // --- User 8: Mesero con experiencia ---
        UserEntity experiencedWaiter = new UserEntity();
        experiencedWaiter.setUsername("exp_waiter");
        experiencedWaiter.setEmail("exp.waiter@example.com");
        experiencedWaiter.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz");
        experiencedWaiter.setFirstName("Sofía");
        experiencedWaiter.setLastName("Ramírez");
        experiencedWaiter.setId(userIdCounter++);
        users.add(experiencedWaiter);

        // --- User 9: Personal de Cocina (Chef Junior) ---
        UserEntity juniorChef = new UserEntity();
        juniorChef.setUsername("junior_chef");
        juniorChef.setEmail("junior.chef@example.com");
        juniorChef.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz");
        juniorChef.setFirstName("Miguel");
        juniorChef.setLastName("Torres");
        juniorChef.setId(userIdCounter++);
        users.add(juniorChef);

        // --- User 10: Auditor de Inventario ---
        UserEntity inventoryAuditor = new UserEntity();
        inventoryAuditor.setUsername("inv_auditor");
        inventoryAuditor.setEmail("inv.auditor@example.com");
        inventoryAuditor.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz");
        inventoryAuditor.setFirstName("Ana");
        inventoryAuditor.setLastName("Díaz");
        inventoryAuditor.setId(userIdCounter++);
        users.add(inventoryAuditor);

        // --- User 11: Administrador de Seguridad ---
        UserEntity securityAdmin = new UserEntity();
        securityAdmin.setUsername("sec_admin");
        securityAdmin.setEmail("sec.admin@example.com");
        securityAdmin.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz");
        securityAdmin.setFirstName("Roberto");
        securityAdmin.setLastName("Sánchez");
        securityAdmin.setId(userIdCounter++);
        users.add(securityAdmin);

        // --- User 12: Cliente VIP ---
        UserEntity vipClient = new UserEntity();
        vipClient.setUsername("vip_client");
        vipClient.setEmail("vip.client@example.com");
        vipClient.setPassword("$2a$10$abcdef0123456789abcdefghijklmnopqrstuvwxyz");
        vipClient.setFirstName("Isabel");
        vipClient.setLastName("Herrera");
        vipClient.setId(userIdCounter++);
        users.add(vipClient);
        
        return users;
	}
	

}
