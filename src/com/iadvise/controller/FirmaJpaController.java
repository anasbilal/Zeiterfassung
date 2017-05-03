/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.controller;

import com.iadvise.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iadvise.entities.Branche;
import com.iadvise.entities.Firma;
import com.iadvise.entities.Projekt;
import java.util.ArrayList;
import java.util.Collection;
import com.iadvise.entities.Kontakt;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Teftazani1
 */
public class FirmaJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4790956922669577576L;

	public FirmaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Firma create(Firma firma) {
        if (firma.getProjektCollection() == null) {
            firma.setProjektCollection(new ArrayList<Projekt>());
        }
        if (firma.getKontaktCollection() == null) {
            firma.setKontaktCollection(new ArrayList<Kontakt>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Branche brid = firma.getBrid();
            if (brid != null) {
                brid = em.getReference(brid.getClass(), brid.getBrID());
                firma.setBrid(brid);
            }
            Collection<Projekt> attachedProjektCollection = new ArrayList<Projekt>();
            for (Projekt projektCollectionProjektToAttach : firma.getProjektCollection()) {
                projektCollectionProjektToAttach = em.getReference(projektCollectionProjektToAttach.getClass(), projektCollectionProjektToAttach.getPrjid());
                attachedProjektCollection.add(projektCollectionProjektToAttach);
            }
            firma.setProjektCollection(attachedProjektCollection);
            Collection<Kontakt> attachedKontaktCollection = new ArrayList<Kontakt>();
            for (Kontakt kontaktCollectionKontaktToAttach : firma.getKontaktCollection()) {
                kontaktCollectionKontaktToAttach = em.getReference(kontaktCollectionKontaktToAttach.getClass(), kontaktCollectionKontaktToAttach.getKontaktid());
                attachedKontaktCollection.add(kontaktCollectionKontaktToAttach);
            }
            firma.setKontaktCollection(attachedKontaktCollection);
            em.persist(firma);
            if (brid != null) {
                brid.getFirmaCollection().add(firma);
                brid = em.merge(brid);
            }
            for (Projekt projektCollectionProjekt : firma.getProjektCollection()) {
                Firma oldFirmaidOfProjektCollectionProjekt = projektCollectionProjekt.getFirmaid();
                projektCollectionProjekt.setFirmaid(firma);
                projektCollectionProjekt = em.merge(projektCollectionProjekt);
                if (oldFirmaidOfProjektCollectionProjekt != null) {
                    oldFirmaidOfProjektCollectionProjekt.getProjektCollection().remove(projektCollectionProjekt);
                    oldFirmaidOfProjektCollectionProjekt = em.merge(oldFirmaidOfProjektCollectionProjekt);
                }
            }
            for (Kontakt kontaktCollectionKontakt : firma.getKontaktCollection()) {
                Firma oldFirmaidOfKontaktCollectionKontakt = kontaktCollectionKontakt.getFirmaid();
                kontaktCollectionKontakt.setFirmaid(firma);
                kontaktCollectionKontakt = em.merge(kontaktCollectionKontakt);
                if (oldFirmaidOfKontaktCollectionKontakt != null) {
                    oldFirmaidOfKontaktCollectionKontakt.getKontaktCollection().remove(kontaktCollectionKontakt);
                    oldFirmaidOfKontaktCollectionKontakt = em.merge(oldFirmaidOfKontaktCollectionKontakt);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return firma;
    }

    public void edit(Firma firma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Firma persistentFirma = em.find(Firma.class, firma.getFirmaid());
            Branche bridOld = persistentFirma.getBrid();
            Branche bridNew = firma.getBrid();
            Collection<Projekt> projektCollectionOld = persistentFirma.getProjektCollection();
            Collection<Projekt> projektCollectionNew = firma.getProjektCollection();
            Collection<Kontakt> kontaktCollectionOld = persistentFirma.getKontaktCollection();
            Collection<Kontakt> kontaktCollectionNew = firma.getKontaktCollection();
            if (bridNew != null) {
                bridNew = em.getReference(bridNew.getClass(), bridNew.getBrID());
                firma.setBrid(bridNew);
            }
            Collection<Projekt> attachedProjektCollectionNew = new ArrayList<Projekt>();
            for (Projekt projektCollectionNewProjektToAttach : projektCollectionNew) {
                projektCollectionNewProjektToAttach = em.getReference(projektCollectionNewProjektToAttach.getClass(), projektCollectionNewProjektToAttach.getPrjid());
                attachedProjektCollectionNew.add(projektCollectionNewProjektToAttach);
            }
            projektCollectionNew = attachedProjektCollectionNew;
            firma.setProjektCollection(projektCollectionNew);
            Collection<Kontakt> attachedKontaktCollectionNew = new ArrayList<Kontakt>();
            for (Kontakt kontaktCollectionNewKontaktToAttach : kontaktCollectionNew) {
                kontaktCollectionNewKontaktToAttach = em.getReference(kontaktCollectionNewKontaktToAttach.getClass(), kontaktCollectionNewKontaktToAttach.getKontaktid());
                attachedKontaktCollectionNew.add(kontaktCollectionNewKontaktToAttach);
            }
            kontaktCollectionNew = attachedKontaktCollectionNew;
            firma.setKontaktCollection(kontaktCollectionNew);
            firma = em.merge(firma);
            if (bridOld != null && !bridOld.equals(bridNew)) {
                bridOld.getFirmaCollection().remove(firma);
                bridOld = em.merge(bridOld);
            }
            if (bridNew != null && !bridNew.equals(bridOld)) {
                bridNew.getFirmaCollection().add(firma);
                bridNew = em.merge(bridNew);
            }
            for (Projekt projektCollectionOldProjekt : projektCollectionOld) {
                if (!projektCollectionNew.contains(projektCollectionOldProjekt)) {
                    projektCollectionOldProjekt.setFirmaid(null);
                    projektCollectionOldProjekt = em.merge(projektCollectionOldProjekt);
                }
            }
            for (Projekt projektCollectionNewProjekt : projektCollectionNew) {
                if (!projektCollectionOld.contains(projektCollectionNewProjekt)) {
                    Firma oldFirmaidOfProjektCollectionNewProjekt = projektCollectionNewProjekt.getFirmaid();
                    projektCollectionNewProjekt.setFirmaid(firma);
                    projektCollectionNewProjekt = em.merge(projektCollectionNewProjekt);
                    if (oldFirmaidOfProjektCollectionNewProjekt != null && !oldFirmaidOfProjektCollectionNewProjekt.equals(firma)) {
                        oldFirmaidOfProjektCollectionNewProjekt.getProjektCollection().remove(projektCollectionNewProjekt);
                        oldFirmaidOfProjektCollectionNewProjekt = em.merge(oldFirmaidOfProjektCollectionNewProjekt);
                    }
                }
            }
            for (Kontakt kontaktCollectionOldKontakt : kontaktCollectionOld) {
                if (!kontaktCollectionNew.contains(kontaktCollectionOldKontakt)) {
                    kontaktCollectionOldKontakt.setFirmaid(null);
                    kontaktCollectionOldKontakt = em.merge(kontaktCollectionOldKontakt);
                }
            }
            for (Kontakt kontaktCollectionNewKontakt : kontaktCollectionNew) {
                if (!kontaktCollectionOld.contains(kontaktCollectionNewKontakt)) {
                    Firma oldFirmaidOfKontaktCollectionNewKontakt = kontaktCollectionNewKontakt.getFirmaid();
                    kontaktCollectionNewKontakt.setFirmaid(firma);
                    kontaktCollectionNewKontakt = em.merge(kontaktCollectionNewKontakt);
                    if (oldFirmaidOfKontaktCollectionNewKontakt != null && !oldFirmaidOfKontaktCollectionNewKontakt.equals(firma)) {
                        oldFirmaidOfKontaktCollectionNewKontakt.getKontaktCollection().remove(kontaktCollectionNewKontakt);
                        oldFirmaidOfKontaktCollectionNewKontakt = em.merge(oldFirmaidOfKontaktCollectionNewKontakt);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = firma.getFirmaid();
                if (findFirma(id) == null) {
                    throw new NonexistentEntityException("The firma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Firma firma;
            try {
                firma = em.getReference(Firma.class, id);
                firma.getFirmaid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The firma with id " + id + " no longer exists.", enfe);
            }
            Branche brid = firma.getBrid();
            if (brid != null) {
                brid.getFirmaCollection().remove(firma);
                brid = em.merge(brid);
            }
            Collection<Projekt> projektCollection = firma.getProjektCollection();
            for (Projekt projektCollectionProjekt : projektCollection) {
                projektCollectionProjekt.setFirmaid(null);
                projektCollectionProjekt = em.merge(projektCollectionProjekt);
            }
            Collection<Kontakt> kontaktCollection = firma.getKontaktCollection();
            for (Kontakt kontaktCollectionKontakt : kontaktCollection) {
                kontaktCollectionKontakt.setFirmaid(null);
                kontaktCollectionKontakt = em.merge(kontaktCollectionKontakt);
            }
            em.remove(firma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Firma> findFirmaEntities() {
        return findFirmaEntities(true, -1, -1);
    }

    public List<Firma> findFirmaEntities(int maxResults, int firstResult) {
        return findFirmaEntities(false, maxResults, firstResult);
    }

    private List<Firma> findFirmaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Firma.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Firma findFirma(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Firma.class, id);
        } finally {
            em.close();
        }
    }

    public int getFirmaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Firma> rt = cq.from(Firma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
