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

import com.iadvise.entities.Firma;
import com.iadvise.entities.Mitarbeiter;
import com.iadvise.entities.Engagement;

import java.util.ArrayList;
import java.util.Collection;

import com.iadvise.entities.Buchung;
import com.iadvise.entities.Projekt;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Teftazani1
 */
public class ProjektJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3652871747360531914L;

	public ProjektJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Projekt create(Projekt projekt) {
        if (projekt.getEngagementCollection() == null) {
            projekt.setEngagementCollection(new ArrayList<Engagement>());
        }
        if (projekt.getBuchungCollection() == null) {
            projekt.setBuchungCollection(new ArrayList<Buchung>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Firma firmaid = projekt.getFirmaid();
            if (firmaid != null) {
                firmaid = em.getReference(firmaid.getClass(), firmaid.getFirmaid());
                projekt.setFirmaid(firmaid);
            }
            Mitarbeiter mitid = projekt.getMitid();
            if (mitid != null) {
                mitid = em.getReference(mitid.getClass(), mitid.getMitid());
                projekt.setMitid(mitid);
            }
            Collection<Engagement> attachedEngagementCollection = new ArrayList<Engagement>();
            for (Engagement engagementCollectionEngagementToAttach : projekt.getEngagementCollection()) {
                engagementCollectionEngagementToAttach = em.getReference(engagementCollectionEngagementToAttach.getClass(), engagementCollectionEngagementToAttach.getEngid());
                attachedEngagementCollection.add(engagementCollectionEngagementToAttach);
            }
            projekt.setEngagementCollection(attachedEngagementCollection);
            Collection<Buchung> attachedBuchungCollection = new ArrayList<Buchung>();
            for (Buchung buchungCollectionBuchungToAttach : projekt.getBuchungCollection()) {
                buchungCollectionBuchungToAttach = em.getReference(buchungCollectionBuchungToAttach.getClass(), buchungCollectionBuchungToAttach.getBuID());
                attachedBuchungCollection.add(buchungCollectionBuchungToAttach);
            }
            projekt.setBuchungCollection(attachedBuchungCollection);
            em.persist(projekt);
            if (firmaid != null) {
                firmaid.getProjektCollection().add(projekt);
                firmaid = em.merge(firmaid);
            }
            if (mitid != null) {
                mitid.getProjektCollection().add(projekt);
                mitid = em.merge(mitid);
            }
            for (Engagement engagementCollectionEngagement : projekt.getEngagementCollection()) {
                Projekt oldPrjidOfEngagementCollectionEngagement = engagementCollectionEngagement.getPrjid();
                engagementCollectionEngagement.setPrjid(projekt);
                engagementCollectionEngagement = em.merge(engagementCollectionEngagement);
                if (oldPrjidOfEngagementCollectionEngagement != null) {
                    oldPrjidOfEngagementCollectionEngagement.getEngagementCollection().remove(engagementCollectionEngagement);
                    oldPrjidOfEngagementCollectionEngagement = em.merge(oldPrjidOfEngagementCollectionEngagement);
                }
            }
            for (Buchung buchungCollectionBuchung : projekt.getBuchungCollection()) {
                Projekt oldPrjidOfBuchungCollectionBuchung = buchungCollectionBuchung.getPrjid();
                buchungCollectionBuchung.setPrjid(projekt);
                buchungCollectionBuchung = em.merge(buchungCollectionBuchung);
                if (oldPrjidOfBuchungCollectionBuchung != null) {
                    oldPrjidOfBuchungCollectionBuchung.getBuchungCollection().remove(buchungCollectionBuchung);
                    oldPrjidOfBuchungCollectionBuchung = em.merge(oldPrjidOfBuchungCollectionBuchung);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return projekt;
    }

    public void edit(Projekt projekt) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projekt persistentProjekt = em.find(Projekt.class, projekt.getPrjid());
            Firma firmaidOld = persistentProjekt.getFirmaid();
            Firma firmaidNew = projekt.getFirmaid();
            Mitarbeiter mitidOld = persistentProjekt.getMitid();
            Mitarbeiter mitidNew = projekt.getMitid();
            Collection<Engagement> engagementCollectionOld = persistentProjekt.getEngagementCollection();
            Collection<Engagement> engagementCollectionNew = projekt.getEngagementCollection();
            Collection<Buchung> buchungCollectionOld = persistentProjekt.getBuchungCollection();
            Collection<Buchung> buchungCollectionNew = projekt.getBuchungCollection();
            if (firmaidNew != null) {
                firmaidNew = em.getReference(firmaidNew.getClass(), firmaidNew.getFirmaid());
                projekt.setFirmaid(firmaidNew);
            }
            if (mitidNew != null) {
                mitidNew = em.getReference(mitidNew.getClass(), mitidNew.getMitid());
                projekt.setMitid(mitidNew);
            }
            Collection<Engagement> attachedEngagementCollectionNew = new ArrayList<Engagement>();
            for (Engagement engagementCollectionNewEngagementToAttach : engagementCollectionNew) {
                engagementCollectionNewEngagementToAttach = em.getReference(engagementCollectionNewEngagementToAttach.getClass(), engagementCollectionNewEngagementToAttach.getEngid());
                attachedEngagementCollectionNew.add(engagementCollectionNewEngagementToAttach);
            }
            engagementCollectionNew = attachedEngagementCollectionNew;
            projekt.setEngagementCollection(engagementCollectionNew);
            Collection<Buchung> attachedBuchungCollectionNew = new ArrayList<Buchung>();
            for (Buchung buchungCollectionNewBuchungToAttach : buchungCollectionNew) {
                buchungCollectionNewBuchungToAttach = em.getReference(buchungCollectionNewBuchungToAttach.getClass(), buchungCollectionNewBuchungToAttach.getBuID());
                attachedBuchungCollectionNew.add(buchungCollectionNewBuchungToAttach);
            }
            buchungCollectionNew = attachedBuchungCollectionNew;
            projekt.setBuchungCollection(buchungCollectionNew);
            projekt = em.merge(projekt);
            if (firmaidOld != null && !firmaidOld.equals(firmaidNew)) {
                firmaidOld.getProjektCollection().remove(projekt);
                firmaidOld = em.merge(firmaidOld);
            }
            if (firmaidNew != null && !firmaidNew.equals(firmaidOld)) {
                firmaidNew.getProjektCollection().add(projekt);
                firmaidNew = em.merge(firmaidNew);
            }
            if (mitidOld != null && !mitidOld.equals(mitidNew)) {
                mitidOld.getProjektCollection().remove(projekt);
                mitidOld = em.merge(mitidOld);
            }
            if (mitidNew != null && !mitidNew.equals(mitidOld)) {
                mitidNew.getProjektCollection().add(projekt);
                mitidNew = em.merge(mitidNew);
            }
            for (Engagement engagementCollectionOldEngagement : engagementCollectionOld) {
                if (!engagementCollectionNew.contains(engagementCollectionOldEngagement)) {
                    engagementCollectionOldEngagement.setPrjid(null);
                    engagementCollectionOldEngagement = em.merge(engagementCollectionOldEngagement);
                }
            }
            for (Engagement engagementCollectionNewEngagement : engagementCollectionNew) {
                if (!engagementCollectionOld.contains(engagementCollectionNewEngagement)) {
                    Projekt oldPrjidOfEngagementCollectionNewEngagement = engagementCollectionNewEngagement.getPrjid();
                    engagementCollectionNewEngagement.setPrjid(projekt);
                    engagementCollectionNewEngagement = em.merge(engagementCollectionNewEngagement);
                    if (oldPrjidOfEngagementCollectionNewEngagement != null && !oldPrjidOfEngagementCollectionNewEngagement.equals(projekt)) {
                        oldPrjidOfEngagementCollectionNewEngagement.getEngagementCollection().remove(engagementCollectionNewEngagement);
                        oldPrjidOfEngagementCollectionNewEngagement = em.merge(oldPrjidOfEngagementCollectionNewEngagement);
                    }
                }
            }
            for (Buchung buchungCollectionOldBuchung : buchungCollectionOld) {
                if (!buchungCollectionNew.contains(buchungCollectionOldBuchung)) {
                    buchungCollectionOldBuchung.setPrjid(null);
                    buchungCollectionOldBuchung = em.merge(buchungCollectionOldBuchung);
                }
            }
            for (Buchung buchungCollectionNewBuchung : buchungCollectionNew) {
                if (!buchungCollectionOld.contains(buchungCollectionNewBuchung)) {
                    Projekt oldPrjidOfBuchungCollectionNewBuchung = buchungCollectionNewBuchung.getPrjid();
                    buchungCollectionNewBuchung.setPrjid(projekt);
                    buchungCollectionNewBuchung = em.merge(buchungCollectionNewBuchung);
                    if (oldPrjidOfBuchungCollectionNewBuchung != null && !oldPrjidOfBuchungCollectionNewBuchung.equals(projekt)) {
                        oldPrjidOfBuchungCollectionNewBuchung.getBuchungCollection().remove(buchungCollectionNewBuchung);
                        oldPrjidOfBuchungCollectionNewBuchung = em.merge(oldPrjidOfBuchungCollectionNewBuchung);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = projekt.getPrjid();
                if (findProjekt(id) == null) {
                    throw new NonexistentEntityException("The projekt with id " + id + " no longer exists.");
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
            Projekt projekt;
            try {
                projekt = em.getReference(Projekt.class, id);
                projekt.getPrjid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projekt with id " + id + " no longer exists.", enfe);
            }
            Firma firmaid = projekt.getFirmaid();
            if (firmaid != null) {
                firmaid.getProjektCollection().remove(projekt);
                firmaid = em.merge(firmaid);
            }
            Mitarbeiter mitid = projekt.getMitid();
            if (mitid != null) {
                mitid.getProjektCollection().remove(projekt);
                mitid = em.merge(mitid);
            }
            Collection<Engagement> engagementCollection = projekt.getEngagementCollection();
            for (Engagement engagementCollectionEngagement : engagementCollection) {
                engagementCollectionEngagement.setPrjid(null);
                engagementCollectionEngagement = em.merge(engagementCollectionEngagement);
            }
            Collection<Buchung> buchungCollection = projekt.getBuchungCollection();
            for (Buchung buchungCollectionBuchung : buchungCollection) {
                buchungCollectionBuchung.setPrjid(null);
                buchungCollectionBuchung = em.merge(buchungCollectionBuchung);
            }
            em.remove(projekt);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Projekt> findProjektEntities() {
        return findProjektEntities(true, -1, -1);
    }

    public List<Projekt> findProjektEntities(int maxResults, int firstResult) {
        return findProjektEntities(false, maxResults, firstResult);
    }

    private List<Projekt> findProjektEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projekt.class));
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

    public Projekt findProjekt(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projekt.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjektCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projekt> rt = cq.from(Projekt.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Projekt findProjektByName(String name){
    	EntityManager em = getEntityManager();
		 Projekt prj = null;
		 Query query = em.createNamedQuery("Projekt.findByProjektname");
		 query.setParameter("projektname", name);
		 List<Projekt> list = query.getResultList( );
		 for( Projekt p:list )
	      {
	         System.out.print("Projektname :"+p.getProjektname());
	         prj = p;
	      }
			
		return prj;
    }
    
}
