package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Kampagne;

public interface KampagneInterface {
	
	public Kampagne add(Kampagne kampagne) throws Exception ;

    public void edit(Kampagne kampagne) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<Kampagne> getAll();

    public Kampagne findById(int ID);

}
