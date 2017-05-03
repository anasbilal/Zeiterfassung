package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.UserRole;

public interface UserRoleInterface {
	
	public UserRole add(UserRole userRole) throws Exception ;

    public void edit(UserRole userRole) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<UserRole> getAll();
    
    public UserRole findById(int ID);

}
