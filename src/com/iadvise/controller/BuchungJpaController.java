/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.controller;

import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Buchung;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iadvise.entities.Mitarbeiter;
import com.iadvise.entities.Projekt;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Teftazani1
 */
public class BuchungJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6717587109617774837L;

	public BuchungJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Buchung create(Buchung buchung) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitarbeiter mitid = buchung.getMitid();
            if (mitid != null) {
                mitid = em.getReference(mitid.getClass(), mitid.getMitid());
                buchung.setMitid(mitid);
            }
            Projekt prjid = buchung.getPrjid();
            if (prjid != null) {
                prjid = em.getReference(prjid.getClass(), prjid.getPrjid());
                buchung.setPrjid(prjid);
            }
            em.persist(buchung);
            if (mitid != null) {
                mitid.getBuchungCollection().add(buchung);
                mitid = em.merge(mitid);
            }
            if (prjid != null) {
                prjid.getBuchungCollection().add(buchung);
                prjid = em.merge(prjid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return buchung;
    }

    public void edit(Buchung buchung) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buchung persistentBuchung = em.find(Buchung.class, buchung.getBuID());
            Mitarbeiter mitidOld = persistentBuchung.getMitid();
            Mitarbeiter mitidNew = buchung.getMitid();
            Projekt prjidOld = persistentBuchung.getPrjid();
            Projekt prjidNew = buchung.getPrjid();
            if (mitidNew != null) {
                mitidNew = em.getReference(mitidNew.getClass(), mitidNew.getMitid());
                buchung.setMitid(mitidNew);
            }
            if (prjidNew != null) {
                prjidNew = em.getReference(prjidNew.getClass(), prjidNew.getPrjid());
                buchung.setPrjid(prjidNew);
            }
            buchung = em.merge(buchung);
            if (mitidOld != null && !mitidOld.equals(mitidNew)) {
                mitidOld.getBuchungCollection().remove(buchung);
                mitidOld = em.merge(mitidOld);
            }
            if (mitidNew != null && !mitidNew.equals(mitidOld)) {
                mitidNew.getBuchungCollection().add(buchung);
                mitidNew = em.merge(mitidNew);
            }
            if (prjidOld != null && !prjidOld.equals(prjidNew)) {
                prjidOld.getBuchungCollection().remove(buchung);
                prjidOld = em.merge(prjidOld);
            }
            if (prjidNew != null && !prjidNew.equals(prjidOld)) {
                prjidNew.getBuchungCollection().add(buchung);
                prjidNew = em.merge(prjidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = buchung.getBuID();
                if (findBuchung(id) == null) {
                    throw new NonexistentEntityException("The buchung with id " + id + " no longer exists.");
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
            Buchung buchung;
            try {
                buchung = em.getReference(Buchung.class, id);
                buchung.getBuID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The buchung with id " + id + " no longer exists.", enfe);
            }
            Mitarbeiter mitid = buchung.getMitid();
            if (mitid != null) {
                mitid.getBuchungCollection().remove(buchung);
                mitid = em.merge(mitid);
            }
            Projekt prjid = buchung.getPrjid();
            if (prjid != null) {
                prjid.getBuchungCollection().remove(buchung);
                prjid = em.merge(prjid);
            }
            em.remove(buchung);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Buchung> findBuchungEntities() {
        return findBuchungEntities(true, -1, -1);
    }

    public List<Buchung> findBuchungEntities(int maxResults, int firstResult) {
        return findBuchungEntities(false, maxResults, firstResult);
    }

    private List<Buchung> findBuchungEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Buchung.class));
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

    public Buchung findBuchung(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Buchung.class, id);
        } finally {
            em.close();
        }
    }

    public int getBuchungCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Buchung> rt = cq.from(Buchung.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
