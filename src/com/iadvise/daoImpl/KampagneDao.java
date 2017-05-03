package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.KampagneJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.KampagneInterface;
import com.iadvise.entities.Kampagne;

public class KampagneDao implements KampagneInterface {
	
	private final KampagneJpaController kampagneController;
    private final EntityManagerFactory emf;

	public KampagneDao() {
		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		kampagneController = new KampagneJpaController(emf);
	}

	@Override
	public Kampagne add(Kampagne kampagne) throws Exception {
		return kampagneController.create(kampagne);
		
	}

	@Override
	public void edit(Kampagne kampagne) throws Exception {
		kampagneController.edit(kampagne);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		kampagneController.destroy(ID);
		
	}

	@Override
	public List<Kampagne> getAll() {
		
		return kampagneController.findKampagneEntities();
	}

	@Override
	public Kampagne findById(int ID) {
		
		return kampagneController.findKampagne(ID);
	}

}
