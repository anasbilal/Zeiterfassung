package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.MitarbeiterJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.MitarbeiterInteface;
import com.iadvise.entities.Mitarbeiter;

public class MitarbeiterDao implements MitarbeiterInteface {
	
	private final MitarbeiterJpaController mitarbeiterController;
    private final EntityManagerFactory emf;

	public MitarbeiterDao() {
		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		mitarbeiterController = new MitarbeiterJpaController(emf);
	}

	@Override
	public Mitarbeiter add(Mitarbeiter mitarbeiter) throws Exception {
		return mitarbeiterController.create(mitarbeiter);
		
	}

	@Override
	public void edit(Mitarbeiter mitarbeiter) throws Exception {
		mitarbeiterController.edit(mitarbeiter);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		mitarbeiterController.destroy(ID);
		
	}

	@Override
	public List<Mitarbeiter> getAll() {
		
		return mitarbeiterController.findMitarbeiterEntities();
	}

	@Override
	public Mitarbeiter findById(int ID) {
		
		return mitarbeiterController.findMitarbeiter(ID);
	}
	
	
	@Override
	public Mitarbeiter findByName(String vorname,String nachname) {
		
		return mitarbeiterController.findMitarbeiterByName(vorname,nachname);
	}

}
