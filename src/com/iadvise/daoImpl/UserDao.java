package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.UsersJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.UserInterface;
import com.iadvise.entities.User;

public class UserDao implements UserInterface {

	private final EntityManagerFactory emf;
	private final UsersJpaController userController;

	public UserDao() {
		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		userController = new UsersJpaController(emf);
	}

	@Override
	public User add(User user) throws Exception {
		return userController.create(user);
		
	}

	@Override
	public void edit(User user) throws Exception {
		userController.edit(user);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		userController.destroy(ID);
		
	}

	@Override
	public List<User> getAll() {
		
		return userController.findUsersEntities();
	}

	@Override
	public User findById(int ID) {
		
		return userController.findUsers(ID);
	}

	@Override
	public User findByUsername(String query) {
		return userController.findUserByUsername(query);
	}

}
