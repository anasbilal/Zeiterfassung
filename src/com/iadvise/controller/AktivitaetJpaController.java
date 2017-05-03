/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.controller;

import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Aktivitaet;
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
public class AktivitaetJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8577094553712033364L;

	public AktivitaetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Aktivitaet create(Aktivitaet aktivitaet) {
        if (aktivitaet.getKontaktCollection() == null) {
            aktivitaet.setKontaktCollection(new ArrayList<Kontakt>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitarbeiter mitid = aktivitaet.getMitid();
            if (mitid != null) {
                mitid = em.getReference(mitid.getClass(), mitid.getMitid());
                aktivitaet.setMitid(mitid);
            }
            Collection<Kontakt> attachedKontaktCollection = new ArrayList<Kontakt>();
            for (Kontakt kontaktCollectionKontaktToAttach : aktivitaet.getKontaktCollection()) {
                kontaktCollectionKontaktToAttach = em.getReference(kontaktCollectionKontaktToAttach.getClass(), kontaktCollectionKontaktToAttach.getKontaktid());
                attachedKontaktCollection.add(kontaktCollectionKontaktToAttach);
            }
            aktivitaet.setKontaktCollection(attachedKontaktCollection);
            em.persist(aktivitaet);
            if (mitid != null) {
                mitid.getAktivitaetCollection().add(aktivitaet);
                mitid = em.merge(mitid);
            }
            for (Kontakt kontaktCollectionKontakt : aktivitaet.getKontaktCollection()) {
                Aktivitaet oldAktidOfKontaktCollectionKontakt = kontaktCollectionKontakt.getAktid();
                kontaktCollectionKontakt.setAktid(aktivitaet);
                kontaktCollectionKontakt = em.merge(kontaktCollectionKontakt);
                if (oldAktidOfKontaktCollectionKontakt != null) {
                    oldAktidOfKontaktCollectionKontakt.getKontaktCollection().remove(kontaktCollectionKontakt);
                    oldAktidOfKontaktCollectionKontakt = em.merge(oldAktidOfKontaktCollectionKontakt);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return aktivitaet;
    }

    public void edit(Aktivitaet aktivitaet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aktivitaet persistentAktivitaet = em.find(Aktivitaet.class, aktivitaet.getAktid());
            Mitarbeiter mitidOld = persistentAktivitaet.getMitid();
            Mitarbeiter mitidNew = aktivitaet.getMitid();
            Collection<Kontakt> kontaktCollectionOld = persistentAktivitaet.getKontaktCollection();
            Collection<Kontakt> kontaktCollectionNew = aktivitaet.getKontaktCollection();
            if (mitidNew != null) {
                mitidNew = em.getReference(mitidNew.getClass(), mitidNew.getMitid());
                aktivitaet.setMitid(mitidNew);
            }
            Collection<Kontakt> attachedKontaktCollectionNew = new ArrayList<Kontakt>();
            for (Kontakt kontaktCollectionNewKontaktToAttach : kontaktCollectionNew) {
                kontaktCollectionNewKontaktToAttach = em.getReference(kontaktCollectionNewKontaktToAttach.getClass(), kontaktCollectionNewKontaktToAttach.getKontaktid());
                attachedKontaktCollectionNew.add(kontaktCollectionNewKontaktToAttach);
            }
            kontaktCollectionNew = attachedKontaktCollectionNew;
            aktivitaet.setKontaktCollection(kontaktCollectionNew);
            aktivitaet = em.merge(aktivitaet);
            if (mitidOld != null && !mitidOld.equals(mitidNew)) {
                mitidOld.getAktivitaetCollection().remove(aktivitaet);
                mitidOld = em.merge(mitidOld);
            }
            if (mitidNew != null && !mitidNew.equals(mitidOld)) {
                mitidNew.getAktivitaetCollection().add(aktivitaet);
                mitidNew = em.merge(mitidNew);
            }
            for (Kontakt kontaktCollectionOldKontakt : kontaktCollectionOld) {
                if (!kontaktCollectionNew.contains(kontaktCollectionOldKontakt)) {
                    kontaktCollectionOldKontakt.setAktid(null);
                    kontaktCollectionOldKontakt = em.merge(kontaktCollectionOldKontakt);
                }
            }
            for (Kontakt kontaktCollectionNewKontakt : kontaktCollectionNew) {
                if (!kontaktCollectionOld.contains(kontaktCollectionNewKontakt)) {
                    Aktivitaet oldAktidOfKontaktCollectionNewKontakt = kontaktCollectionNewKontakt.getAktid();
                    kontaktCollectionNewKontakt.setAktid(aktivitaet);
                    kontaktCollectionNewKontakt = em.merge(kontaktCollectionNewKontakt);
                    if (oldAktidOfKontaktCollectionNewKontakt != null && !oldAktidOfKontaktCollectionNewKontakt.equals(aktivitaet)) {
                        oldAktidOfKontaktCollectionNewKontakt.getKontaktCollection().remove(kontaktCollectionNewKontakt);
                        oldAktidOfKontaktCollectionNewKontakt = em.merge(oldAktidOfKontaktCollectionNewKontakt);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aktivitaet.getAktid();
                if (findAktivitaet(id) == null) {
                    throw new NonexistentEntityException("The aktivitaet with id " + id + " no longer exists.");
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
            Aktivitaet aktivitaet;
            try {
                aktivitaet = em.getReference(Aktivitaet.class, id);
                aktivitaet.getAktid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aktivitaet with id " + id + " no longer exists.", enfe);
            }
            Mitarbeiter mitid = aktivitaet.getMitid();
            if (mitid != null) {
                mitid.getAktivitaetCollection().remove(aktivitaet);
                mitid = em.merge(mitid);
            }
            Collection<Kontakt> kontaktCollection = aktivitaet.getKontaktCollection();
            for (Kontakt kontaktCollectionKontakt : kontaktCollection) {
                kontaktCollectionKontakt.setAktid(null);
                kontaktCollectionKontakt = em.merge(kontaktCollectionKontakt);
            }
            em.remove(aktivitaet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aktivitaet> findAktivitaetEntities() {
        return findAktivitaetEntities(true, -1, -1);
    }

    public List<Aktivitaet> findAktivitaetEntities(int maxResults, int firstResult) {
        return findAktivitaetEntities(false, maxResults, firstResult);
    }

    private List<Aktivitaet> findAktivitaetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aktivitaet.class));
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

    public Aktivitaet findAktivitaet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aktivitaet.class, id);
        } finally {
            em.close();
        }
    }

    public int getAktivitaetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aktivitaet> rt = cq.from(Aktivitaet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
