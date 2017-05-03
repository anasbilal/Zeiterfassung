package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.UserRolesJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.UserRoleInterface;
import com.iadvise.entities.UserRole;

public class UserRoleDao implements UserRoleInterface {
	
	private final EntityManagerFactory emf;
	private final UserRolesJpaController userRolesController;

	public UserRoleDao() {
		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		userRolesController = new UserRolesJpaController(emf);
	}

	@Override
	public UserRole add(UserRole userRole) throws Exception {
		return userRolesController.create(userRole);
		
	}

	@Override
	public void edit(UserRole userRole) throws Exception {
		userRolesController.edit(userRole);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		userRolesController.destroy(ID);
		
	}

	@Override
	public List<UserRole> getAll() {
		
		return userRolesController.findUserRolesEntities();
	}

	@Override
	public UserRole findById(int ID) {
		
		return userRolesController.findUserRoles(ID);
	}

}
