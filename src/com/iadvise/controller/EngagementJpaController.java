/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.controller;

import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Engagement;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iadvise.entities.Projekt;
import com.iadvise.entities.Mitarbeiter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Teftazani1
 */
public class EngagementJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2617132529404841407L;

	public EngagementJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Engagement create(Engagement engagement) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projekt prjid = engagement.getPrjid();
            if (prjid != null) {
                prjid = em.getReference(prjid.getClass(), prjid.getPrjid());
                engagement.setPrjid(prjid);
            }
            Mitarbeiter mitid = engagement.getMitid();
            if (mitid != null) {
                mitid = em.getReference(mitid.getClass(), mitid.getMitid());
                engagement.setMitid(mitid);
            }
            em.persist(engagement);
            if (prjid != null) {
                prjid.getEngagementCollection().add(engagement);
                prjid = em.merge(prjid);
            }
            if (mitid != null) {
                mitid.getEngagementCollection().add(engagement);
                mitid = em.merge(mitid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return engagement;
    }

    public void edit(Engagement engagement) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Engagement persistentEngagement = em.find(Engagement.class, engagement.getEngid());
            Projekt prjidOld = persistentEngagement.getPrjid();
            Projekt prjidNew = engagement.getPrjid();
            Mitarbeiter mitidOld = persistentEngagement.getMitid();
            Mitarbeiter mitidNew = engagement.getMitid();
            if (prjidNew != null) {
                prjidNew = em.getReference(prjidNew.getClass(), prjidNew.getPrjid());
                engagement.setPrjid(prjidNew);
            }
            if (mitidNew != null) {
                mitidNew = em.getReference(mitidNew.getClass(), mitidNew.getMitid());
                engagement.setMitid(mitidNew);
            }
            engagement = em.merge(engagement);
            if (prjidOld != null && !prjidOld.equals(prjidNew)) {
                prjidOld.getEngagementCollection().remove(engagement);
                prjidOld = em.merge(prjidOld);
            }
            if (prjidNew != null && !prjidNew.equals(prjidOld)) {
                prjidNew.getEngagementCollection().add(engagement);
                prjidNew = em.merge(prjidNew);
            }
            if (mitidOld != null && !mitidOld.equals(mitidNew)) {
                mitidOld.getEngagementCollection().remove(engagement);
                mitidOld = em.merge(mitidOld);
            }
            if (mitidNew != null && !mitidNew.equals(mitidOld)) {
                mitidNew.getEngagementCollection().add(engagement);
                mitidNew = em.merge(mitidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = engagement.getEngid();
                if (findEngagement(id) == null) {
                    throw new NonexistentEntityException("The engagement with id " + id + " no longer exists.");
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
            Engagement engagement;
            try {
                engagement = em.getReference(Engagement.class, id);
                engagement.getEngid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The engagement with id " + id + " no longer exists.", enfe);
            }
            Projekt prjid = engagement.getPrjid();
            if (prjid != null) {
                prjid.getEngagementCollection().remove(engagement);
                prjid = em.merge(prjid);
            }
            Mitarbeiter mitid = engagement.getMitid();
            if (mitid != null) {
                mitid.getEngagementCollection().remove(engagement);
                mitid = em.merge(mitid);
            }
            em.remove(engagement);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Engagement> findEngagementEntities() {
        return findEngagementEntities(true, -1, -1);
    }

    public List<Engagement> findEngagementEntities(int maxResults, int firstResult) {
        return findEngagementEntities(false, maxResults, firstResult);
    }

    private List<Engagement> findEngagementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Engagement.class));
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

    public Engagement findEngagement(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Engagement.class, id);
        } finally {
            em.close();
        }
    }

    public int getEngagementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Engagement> rt = cq.from(Engagement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
