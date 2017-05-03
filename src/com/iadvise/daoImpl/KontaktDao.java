package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.KontaktJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.KontaktInterface;
import com.iadvise.entities.Kontakt;

public class KontaktDao implements KontaktInterface {
	
	private final KontaktJpaController KontaktController;
    private final EntityManagerFactory emf;

	public KontaktDao() {
		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		KontaktController = new KontaktJpaController(emf);
	}

	@Override
	public Kontakt add(Kontakt Kontakt) throws Exception {
		return KontaktController.create(Kontakt);
		
	}

	@Override
	public void edit(Kontakt Kontakt) throws Exception {
		KontaktController.edit(Kontakt);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		KontaktController.destroy(ID);
		
	}

	@Override
	public List<Kontakt> getAll() {
		
		return KontaktController.findKontaktEntities();
	}

	@Override
	public Kontakt findById(int ID) {
		
		return KontaktController.findKontakt(ID);
	}

}
