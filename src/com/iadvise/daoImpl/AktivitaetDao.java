package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.AktivitaetJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.AktivitaetInterface;
import com.iadvise.entities.Aktivitaet;

public class AktivitaetDao implements AktivitaetInterface {

	private final AktivitaetJpaController aktivitaetController;
	private final EntityManagerFactory emf;

	public AktivitaetDao() {

		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		aktivitaetController = new AktivitaetJpaController(emf);

	}

	@Override
	public Aktivitaet add(Aktivitaet aktivitaet) throws Exception {
		return aktivitaetController.create(aktivitaet);

	}

	@Override
	public void edit(Aktivitaet aktivitaet) throws Exception {
		aktivitaetController.edit(aktivitaet);

	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		aktivitaetController.destroy(ID);

	}

	@Override
	public List<Aktivitaet> getAll() {

		return aktivitaetController.findAktivitaetEntities();
	}

	@Override
	public Aktivitaet findById(int ID) {

		return aktivitaetController.findAktivitaet(ID);
	}

}
