package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Buchung;

public interface BuchungInterface {
	
	public Buchung add(Buchung buchung) throws Exception ;

    public void edit(Buchung buchung) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<Buchung> getAll();

    public Buchung findById(int ID);


}
