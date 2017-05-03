package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.User;

public interface UserInterface {
	
	public User add(User user) throws Exception ;

    public void edit(User user) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<User> getAll();
    
    public User findById(int ID);
    
    public User findByUsername(String query);


}
