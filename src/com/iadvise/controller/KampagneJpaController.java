/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.controller;

import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Kampagne;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iadvise.entities.Mitarbeiter;
import com.iadvise.entities.Kontakt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Teftazani1
 */
public class KampagneJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2278727066591711260L;

	public KampagneJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Kampagne create(Kampagne kampagne) {
        if (kampagne.getKontaktCollection() == null) {
            kampagne.setKontaktCollection(new ArrayList<Kontakt>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitarbeiter mitid = kampagne.getMitid();
            if (mitid != null) {
                mitid = em.getReference(mitid.getClass(), mitid.getMitid());
                kampagne.setMitid(mitid);
            }
            Collection<Kontakt> attachedKontaktCollection = new ArrayList<Kontakt>();
            for (Kontakt kontaktCollectionKontaktToAttach : kampagne.getKontaktCollection()) {
                kontaktCollectionKontaktToAttach = em.getReference(kontaktCollectionKontaktToAttach.getClass(), kontaktCollectionKontaktToAttach.getKontaktid());
                attachedKontaktCollection.add(kontaktCollectionKontaktToAttach);
            }
            kampagne.setKontaktCollection(attachedKontaktCollection);
            em.persist(kampagne);
            if (mitid != null) {
                mitid.getKampagneCollection().add(kampagne);
                mitid = em.merge(mitid);
            }
            for (Kontakt kontaktCollectionKontakt : kampagne.getKontaktCollection()) {
                Kampagne oldKampidOfKontaktCollectionKontakt = kontaktCollectionKontakt.getKampid();
                kontaktCollectionKontakt.setKampid(kampagne);
                kontaktCollectionKontakt = em.merge(kontaktCollectionKontakt);
                if (oldKampidOfKontaktCollectionKontakt != null) {
                    oldKampidOfKontaktCollectionKontakt.getKontaktCollection().remove(kontaktCollectionKontakt);
                    oldKampidOfKontaktCollectionKontakt = em.merge(oldKampidOfKontaktCollectionKontakt);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return kampagne;
    }

    public void edit(Kampagne kampagne) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kampagne persistentKampagne = em.find(Kampagne.class, kampagne.getKampid());
            Mitarbeiter mitidOld = persistentKampagne.getMitid();
            Mitarbeiter mitidNew = kampagne.getMitid();
            Collection<Kontakt> kontaktCollectionOld = persistentKampagne.getKontaktCollection();
            Collection<Kontakt> kontaktCollectionNew = kampagne.getKontaktCollection();
            if (mitidNew != null) {
                mitidNew = em.getReference(mitidNew.getClass(), mitidNew.getMitid());
                kampagne.setMitid(mitidNew);
            }
            Collection<Kontakt> attachedKontaktCollectionNew = new ArrayList<Kontakt>();
            for (Kontakt kontaktCollectionNewKontaktToAttach : kontaktCollectionNew) {
                kontaktCollectionNewKontaktToAttach = em.getReference(kontaktCollectionNewKontaktToAttach.getClass(), kontaktCollectionNewKontaktToAttach.getKontaktid());
                attachedKontaktCollectionNew.add(kontaktCollectionNewKontaktToAttach);
            }
            kontaktCollectionNew = attachedKontaktCollectionNew;
            kampagne.setKontaktCollection(kontaktCollectionNew);
            kampagne = em.merge(kampagne);
            if (mitidOld != null && !mitidOld.equals(mitidNew)) {
                mitidOld.getKampagneCollection().remove(kampagne);
                mitidOld = em.merge(mitidOld);
            }
            if (mitidNew != null && !mitidNew.equals(mitidOld)) {
                mitidNew.getKampagneCollection().add(kampagne);
                mitidNew = em.merge(mitidNew);
            }
            for (Kontakt kontaktCollectionOldKontakt : kontaktCollectionOld) {
                if (!kontaktCollectionNew.contains(kontaktCollectionOldKontakt)) {
                    kontaktCollectionOldKontakt.setKampid(null);
                    kontaktCollectionOldKontakt = em.merge(kontaktCollectionOldKontakt);
                }
            }
            for (Kontakt kontaktCollectionNewKontakt : kontaktCollectionNew) {
                if (!kontaktCollectionOld.contains(kontaktCollectionNewKontakt)) {
                    Kampagne oldKampidOfKontaktCollectionNewKontakt = kontaktCollectionNewKontakt.getKampid();
                    kontaktCollectionNewKontakt.setKampid(kampagne);
                    kontaktCollectionNewKontakt = em.merge(kontaktCollectionNewKontakt);
                    if (oldKampidOfKontaktCollectionNewKontakt != null && !oldKampidOfKontaktCollectionNewKontakt.equals(kampagne)) {
                        oldKampidOfKontaktCollectionNewKontakt.getKontaktCollection().remove(kontaktCollectionNewKontakt);
                        oldKampidOfKontaktCollectionNewKontakt = em.merge(oldKampidOfKontaktCollectionNewKontakt);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = kampagne.getKampid();
                if (findKampagne(id) == null) {
                    throw new NonexistentEntityException("The kampagne with id " + id + " no longer exists.");
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
            Kampagne kampagne;
            try {
                kampagne = em.getReference(Kampagne.class, id);
                kampagne.getKampid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kampagne with id " + id + " no longer exists.", enfe);
            }
            Mitarbeiter mitid = kampagne.getMitid();
            if (mitid != null) {
                mitid.getKampagneCollection().remove(kampagne);
                mitid = em.merge(mitid);
            }
            Collection<Kontakt> kontaktCollection = kampagne.getKontaktCollection();
            for (Kontakt kontaktCollectionKontakt : kontaktCollection) {
                kontaktCollectionKontakt.setKampid(null);
                kontaktCollectionKontakt = em.merge(kontaktCollectionKontakt);
            }
            em.remove(kampagne);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kampagne> findKampagneEntities() {
        return findKampagneEntities(true, -1, -1);
    }

    public List<Kampagne> findKampagneEntities(int maxResults, int firstResult) {
        return findKampagneEntities(false, maxResults, firstResult);
    }

    private List<Kampagne> findKampagneEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kampagne.class));
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

    public Kampagne findKampagne(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kampagne.class, id);
        } finally {
            em.close();
        }
    }

    public int getKampagneCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kampagne> rt = cq.from(Kampagne.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
