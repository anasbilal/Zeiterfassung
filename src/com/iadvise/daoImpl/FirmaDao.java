package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.FirmaJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.FirmaInterface;
import com.iadvise.entities.Firma;

public class FirmaDao implements FirmaInterface {
	
	private final FirmaJpaController firmaController;
    private final EntityManagerFactory emf;

	public FirmaDao() {
		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		firmaController = new FirmaJpaController(emf);
	}

	@Override
	public Firma add(Firma firma) throws Exception {
		return firmaController.create(firma);
	}

	@Override
	public void edit(Firma firma) throws Exception {
		firmaController.edit(firma);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		firmaController.destroy(ID);
		
	}

	@Override
	public List<Firma> getAll() {
		
		return firmaController.findFirmaEntities();
	}

	@Override
	public Firma findById(int ID) {
		
		return firmaController.findFirma(ID);
	}

}
