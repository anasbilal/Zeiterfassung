package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.AktivitaetJpaController;
import com.iadvise.controller.BuchungJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.BuchungInterface;
import com.iadvise.entities.Buchung;

public class BuchungDao implements BuchungInterface {
	
	private final BuchungJpaController buchungController;
	private final EntityManagerFactory emf;
	
	public BuchungDao() {

		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		buchungController = new BuchungJpaController(emf);

	}

	@Override
	public Buchung add(Buchung buchung) throws Exception {
		return buchungController.create(buchung);
	}

	@Override
	public void edit(Buchung buchung) throws Exception {
		buchungController.edit(buchung);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		buchungController.destroy(ID);
		
	}

	@Override
	public List<Buchung> getAll() {
		
		return buchungController.findBuchungEntities();
	}

	@Override
	public Buchung findById(int ID) {
		
		return buchungController.findBuchung(ID);
	}
	

}
