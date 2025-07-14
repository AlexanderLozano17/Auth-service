package com.authservice.dataInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.authservice.entities.RoleEntity;
import com.authservice.entities.UserEntity;
import com.authservice.entities.UserRoleEntity;
import com.authservice.repositories.RoleJpaRepository;
import com.authservice.repositories.UserJpaRepository;
import com.authservice.repositories.UserRoleJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRoleAssignmentInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleAssignmentInitializer.class);
	
	private final UserJpaRepository userJpaRepository;
	private final RoleJpaRepository roleJpaRepository;
	private final UserRoleJpaRepository userRoleJpaRepository;

	@Transactional
	public void userRolesRun() {
		LOGGER.info("Iniciando verificación e inicialización de datos de UserRole...");

		if (roleJpaRepository.count() > 0 && userJpaRepository.count() > 0) {
			LOGGER.info("No se encontraron UserRole. Inicializando con datos por defecto.");

			try {
				List<UserRoleEntity> listUserRoles = getUsersWithRoles();				
				userRoleJpaRepository.saveAll(listUserRoles);

			} catch (Exception e) {
				LOGGER.error("Error al inicializar los datos de UserRoleUserRole: {}", e.getMessage(), e);
			}
		} else {
			LOGGER.info("Ya existen UserRole en la base de datos. No se requiere inicialización.");
		}
		LOGGER.info("Finalizada la verificación e inicialización de datos de UserRole.");
		
	}
	
	public List<UserRoleEntity> getUsersWithRoles() {
		
		List<UserRoleEntity> listUserRoles = new ArrayList<>();
		
        // --- Obtener los roles una sola vez para reutilizarlos ---
        RoleEntity superAdminRole = roleJpaRepository.findByName("SUPER_ADMIN").orElseThrow(() -> new NoSuchElementException("Rol SUPER_ADMIN no encontrado"));
        RoleEntity adminRole = roleJpaRepository.findByName("ADMIN").orElseThrow(() -> new NoSuchElementException("Rol ADMIN no encontrado"));
        RoleEntity auditorRole = roleJpaRepository.findByName("AUDITOR").orElseThrow(() -> new NoSuchElementException("Rol AUDITOR no encontrado"));
        RoleEntity securityAdminRole = roleJpaRepository.findByName("SECURITY_ADMIN").orElseThrow(() -> new NoSuchElementException("Rol SECURITY_ADMIN no encontrado"));
        RoleEntity waiterRole = roleJpaRepository.findByName("WAITER").orElseThrow(() -> new NoSuchElementException("Rol WAITER no encontrado"));
        RoleEntity chefRole = roleJpaRepository.findByName("CHEF").orElseThrow(() -> new NoSuchElementException("Rol CHEF no encontrado"));
        RoleEntity cashierRole = roleJpaRepository.findByName("CASHIER").orElseThrow(() -> new NoSuchElementException("Rol CASHIER no encontrado"));
        RoleEntity inventoryStaffRole = roleJpaRepository.findByName("INVENTORY_STAFF").orElseThrow(() -> new NoSuchElementException("Rol INVENTORY_STAFF no encontrado"));
        RoleEntity customerRole = roleJpaRepository.findByName("CUSTOMER").orElseThrow(() -> new NoSuchElementException("Rol CUSTOMER no encontrado"));
        RoleEntity managerRole = roleJpaRepository.findByName("MANAGER").orElseThrow(() -> new NoSuchElementException("Rol MANAGER no encontrado"));

        // --- Asignación de Roles a Usuarios ---
        // Nota: Los usuarios deben existir previamente en la base de datos para ser encontrados por email.

        // 1. Usuario Super Administrador: admin_user
        UserEntity alexanderLozano = userJpaRepository.findByEmail("alexander.lozano@example.com").orElseThrow(() -> new NoSuchElementException("Usuario alexander.lozano@example.com no encontrado"));
        UserRoleEntity userRoleEntity1 = new UserRoleEntity(alexanderLozano, superAdminRole);
        listUserRoles.add(userRoleEntity1); // Guarda la relación explícita
        LOGGER.info("Asignado SUPER_ADMIN a " + alexanderLozano.getUsername()); 

        // 2. Usuario Administrador General: johndoe (email de ejemplo del Canvas original)
        UserEntity johnDoe = userJpaRepository.findByEmail("john.doe@example.com").orElseThrow(() -> new NoSuchElementException("Usuario john.doe@example.com no encontrado"));
        UserRoleEntity userRoleEntity2 = new UserRoleEntity(johnDoe, adminRole);
        listUserRoles.add(userRoleEntity2);
        LOGGER.info("Asignado ADMIN a " + johnDoe.getUsername());

        // 3. Usuario Auditor: janedoe (email de ejemplo del Canvas original)
        UserEntity janeDoe = userJpaRepository.findByEmail("jane.doe@example.com").orElseThrow(() -> new NoSuchElementException("Usuario jane.doe@example.com no encontrado"));
        UserRoleEntity userRoleEntity3 = new UserRoleEntity(janeDoe, auditorRole);
        listUserRoles.add(userRoleEntity3);
        LOGGER.info("Asignado AUDITOR a " + janeDoe.getUsername());

        // 4. Usuario Administrador de Seguridad: sec_admin (email de ejemplo del Canvas original)
        UserEntity robertoSanchez = userJpaRepository.findByEmail("sec.admin@example.com").orElseThrow(() -> new NoSuchElementException("Usuario sec.admin@example.com no encontrado"));
        UserRoleEntity userRoleEntity4 = new UserRoleEntity(robertoSanchez, securityAdminRole);
        listUserRoles.add(userRoleEntity4);
        LOGGER.info("Asignado SECURITY_ADMIN a " + robertoSanchez.getUsername());

        // 5. Usuario Mesero: juan.mesero (email de ejemplo del Canvas original)
        UserEntity freddyMesero = userJpaRepository.findByEmail("freddy@example.com").orElseThrow(() -> new NoSuchElementException("Usuario juan.mesero@example.com no encontrado"));
        UserRoleEntity userRoleEntity5 = new UserRoleEntity(freddyMesero, waiterRole);
        listUserRoles.add(userRoleEntity5);
        LOGGER.info("Asignado WAITER a " + freddyMesero.getUsername());

        // 6. Usuario con Múltiples Roles: gerente.cajero (MANAGER y CASHIER)
        UserEntity gerenteCajero = userJpaRepository.findByEmail("dalas@example.com").orElseThrow(() -> new NoSuchElementException("Usuario gerente.cajero@example.com no encontrado"));
        UserRoleEntity userRoleEntity10a = new UserRoleEntity(gerenteCajero, managerRole);
        listUserRoles.add(userRoleEntity10a); // Asigna el rol MANAGER

        UserRoleEntity userRoleEntity10b = new UserRoleEntity(gerenteCajero, cashierRole);
        listUserRoles.add(userRoleEntity10b); // Asigna el rol CASHIER
        LOGGER.info("Asignado MANAGER y CASHIER a " + gerenteCajero.getUsername());

        // 7. Usuario Mesero con experiencia: exp_waiter
        UserEntity expWaiter = userJpaRepository.findByEmail("exp.waiter@example.com").orElseThrow(() -> new NoSuchElementException("Usuario exp.waiter@example.com no encontrado"));
        UserRoleEntity userRoleEntity11 = new UserRoleEntity(expWaiter, waiterRole);
        listUserRoles.add(userRoleEntity11);
        LOGGER.info("Asignado WAITER a " + expWaiter.getUsername());

        // 8. Usuario Cocinero Junior: junior_chef
        UserEntity juniorChef = userJpaRepository.findByEmail("junior.chef@example.com").orElseThrow(() -> new NoSuchElementException("Usuario junior.chef@example.com no encontrado"));
        UserRoleEntity userRoleEntity12 = new UserRoleEntity(juniorChef, chefRole);
        listUserRoles.add(userRoleEntity12);
        LOGGER.info("Asignado CHEF a " + juniorChef.getUsername());

        // 9. Usuario Auditor de Inventario: inv_auditor
        UserEntity invAuditor = userJpaRepository.findByEmail("inv.auditor@example.com").orElseThrow(() -> new NoSuchElementException("Usuario inv.auditor@example.com no encontrado"));
        UserRoleEntity userRoleEntity13 = new UserRoleEntity(invAuditor, auditorRole);
        listUserRoles.add(userRoleEntity13);
        LOGGER.info("Asignado AUDITOR a " + invAuditor.getUsername());

        // 10. Usuario Administrador de Seguridad: sec_admin
        UserEntity secAdmin = userJpaRepository.findByEmail("sec.admin@example.com").orElseThrow(() -> new NoSuchElementException("Usuario sec.admin@example.com no encontrado"));
        UserRoleEntity userRoleEntity14 = new UserRoleEntity(secAdmin, securityAdminRole);
        listUserRoles.add(userRoleEntity14);
        LOGGER.info("Asignado SECURITY_ADMIN a " + secAdmin.getUsername());

        // 11. Usuario Cliente VIP: vip_client
        UserEntity vipClient = userJpaRepository.findByEmail("vip.client@example.com").orElseThrow(() -> new NoSuchElementException("Usuario vip.client@example.com no encontrado"));
        UserRoleEntity userRoleEntity15 = new UserRoleEntity(vipClient, customerRole);
        listUserRoles.add(userRoleEntity15);
        LOGGER.info("Asignado CUSTOMER a " + vipClient.getUsername());

        LOGGER.info("Asignación de roles completada.");
        
        return listUserRoles;
    }
	
	
}
