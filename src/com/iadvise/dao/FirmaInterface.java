package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Firma;

public interface FirmaInterface {
	
	public Firma add(Firma firma) throws Exception ;

    public void edit(Firma firma) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<Firma> getAll();

    public Firma findById(int ID);

}
