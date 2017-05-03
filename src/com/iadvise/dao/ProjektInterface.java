package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Projekt;

public interface ProjektInterface {
	
	public Projekt add(Projekt projekt) throws Exception ;

    public void edit(Projekt projekt) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<Projekt> getAll();
    
    public Projekt findById(int ID);
    
    public Projekt findByName(String name);

}
