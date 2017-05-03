package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Kontakt;

public interface KontaktInterface {
	
	public Kontakt add(Kontakt ontakt) throws Exception ;

    public void edit(Kontakt kontakt) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<Kontakt> getAll();
    
    public Kontakt findById(int ID);


}
