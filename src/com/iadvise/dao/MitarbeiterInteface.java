package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Mitarbeiter;

public interface MitarbeiterInteface {
	
	public Mitarbeiter add(Mitarbeiter mitarbeiter) throws Exception ;

    public void edit(Mitarbeiter mitarbeiter) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<Mitarbeiter> getAll();
    
    public Mitarbeiter findById(int ID);

	public Mitarbeiter findByName(String vorname,String nachname);


}
