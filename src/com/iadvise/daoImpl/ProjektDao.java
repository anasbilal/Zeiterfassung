package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.ProjektJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.ProjektInterface;
import com.iadvise.entities.Projekt;

public class ProjektDao implements ProjektInterface {
	
	private final ProjektJpaController projektController;
    private final EntityManagerFactory emf;

	public ProjektDao() {
		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		projektController = new ProjektJpaController(emf);
	}

	@Override
	public Projekt add(Projekt projekt) throws Exception {
		return projektController.create(projekt);
		
	}

	@Override
	public void edit(Projekt projekt) throws Exception {
		projektController.edit(projekt);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		projektController.destroy(ID);
		
	}

	@Override
	public List<Projekt> getAll() {
		
		return projektController.findProjektEntities();
	}

	@Override
	public Projekt findById(int ID) {
		
		return projektController.findProjekt(ID);
	}

	public Projekt findByName(String name) {
		return projektController.findProjektByName(name);
		
	}

}
