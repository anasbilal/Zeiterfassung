package com.iadvise.dao;

import java.util.List;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Aktivitaet;

/**
 *
 * @author Teftazani1
 */
public interface AktivitaetInterface {
   
    public Aktivitaet add(Aktivitaet aktivitaet) throws Exception ;

    public void edit(Aktivitaet aktivitaet) throws Exception;

    public void remove(int ID) throws NonexistentEntityException, IllegalOrphanException;

    public List<Aktivitaet> getAll();

    public Aktivitaet findById(int ID);

}