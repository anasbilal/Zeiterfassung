package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.BrancheJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.BrancheInterface;
import com.iadvise.entities.Branche;

public class BrancheDao implements BrancheInterface {
	
	private final BrancheJpaController brancheController;
    private final EntityManagerFactory emf;

	public BrancheDao() {
		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		brancheController = new BrancheJpaController(emf);
	}

	@Override
	public Branche add(Branche branche) throws Exception {
		return brancheController.create(branche);
		
		
	}

	@Override
	public void edit(Branche branche) throws Exception {
		brancheController.edit(branche);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		brancheController.destroy(ID);
		
	}

	@Override
	public List<Branche> getAll() {
		
		return brancheController.findBrancheEntities();
	}

	@Override
	public Branche findById(int ID) {
		
		return brancheController.findBranche(ID);
	}

}
