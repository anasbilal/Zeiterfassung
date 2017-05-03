package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Engagement;

public interface EngagementInterface {
	
	public Engagement add(Engagement engagement) throws Exception ;

    public void edit(Engagement engagement) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<Engagement> getAll();

    public Engagement findById(int ID);

}
