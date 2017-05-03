package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Branche;

/**
 *
 * @author Teftazani1
 */
public interface BrancheInterface {
   
    public Branche add(Branche branche) throws Exception ;

    public void edit(Branche branche) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<Branche> getAll();

    public Branche findById(int ID);

}