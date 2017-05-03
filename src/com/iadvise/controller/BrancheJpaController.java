/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.controller;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Branche;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iadvise.entities.Firma;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Teftazani1
 */
public class BrancheJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -588854258717850030L;

	public BrancheJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Branche create(Branche branche) {
        if (branche.getFirmaCollection() == null) {
            branche.setFirmaCollection(new ArrayList<Firma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Firma> attachedFirmaCollection = new ArrayList<Firma>();
            for (Firma firmaCollectionFirmaToAttach : branche.getFirmaCollection()) {
                firmaCollectionFirmaToAttach = em.getReference(firmaCollectionFirmaToAttach.getClass(), firmaCollectionFirmaToAttach.getFirmaid());
                attachedFirmaCollection.add(firmaCollectionFirmaToAttach);
            }
            branche.setFirmaCollection(attachedFirmaCollection);
            em.persist(branche);
            for (Firma firmaCollectionFirma : branche.getFirmaCollection()) {
                Branche oldBridOfFirmaCollectionFirma = firmaCollectionFirma.getBrid();
                firmaCollectionFirma.setBrid(branche);
                firmaCollectionFirma = em.merge(firmaCollectionFirma);
                if (oldBridOfFirmaCollectionFirma != null) {
                    oldBridOfFirmaCollectionFirma.getFirmaCollection().remove(firmaCollectionFirma);
                    oldBridOfFirmaCollectionFirma = em.merge(oldBridOfFirmaCollectionFirma);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return branche;
    }

    public void edit(Branche branche) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Branche persistentBranche = em.find(Branche.class, branche.getBrID());
            Collection<Firma> firmaCollectionOld = persistentBranche.getFirmaCollection();
            Collection<Firma> firmaCollectionNew = branche.getFirmaCollection();
            List<String> illegalOrphanMessages = null;
            for (Firma firmaCollectionOldFirma : firmaCollectionOld) {
                if (!firmaCollectionNew.contains(firmaCollectionOldFirma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Firma " + firmaCollectionOldFirma + " since its brid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Firma> attachedFirmaCollectionNew = new ArrayList<Firma>();
            for (Firma firmaCollectionNewFirmaToAttach : firmaCollectionNew) {
                firmaCollectionNewFirmaToAttach = em.getReference(firmaCollectionNewFirmaToAttach.getClass(), firmaCollectionNewFirmaToAttach.getFirmaid());
                attachedFirmaCollectionNew.add(firmaCollectionNewFirmaToAttach);
            }
            firmaCollectionNew = attachedFirmaCollectionNew;
            branche.setFirmaCollection(firmaCollectionNew);
            branche = em.merge(branche);
            for (Firma firmaCollectionNewFirma : firmaCollectionNew) {
                if (!firmaCollectionOld.contains(firmaCollectionNewFirma)) {
                    Branche oldBridOfFirmaCollectionNewFirma = firmaCollectionNewFirma.getBrid();
                    firmaCollectionNewFirma.setBrid(branche);
                    firmaCollectionNewFirma = em.merge(firmaCollectionNewFirma);
                    if (oldBridOfFirmaCollectionNewFirma != null && !oldBridOfFirmaCollectionNewFirma.equals(branche)) {
                        oldBridOfFirmaCollectionNewFirma.getFirmaCollection().remove(firmaCollectionNewFirma);
                        oldBridOfFirmaCollectionNewFirma = em.merge(oldBridOfFirmaCollectionNewFirma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = branche.getBrID();
                if (findBranche(id) == null) {
                    throw new NonexistentEntityException("The branche with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Branche branche;
            try {
                branche = em.getReference(Branche.class, id);
                branche.getBrID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The branche with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Firma> firmaCollectionOrphanCheck = branche.getFirmaCollection();
            for (Firma firmaCollectionOrphanCheckFirma : firmaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Branche (" + branche + ") cannot be destroyed since the Firma " + firmaCollectionOrphanCheckFirma + " in its firmaCollection field has a non-nullable brid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(branche);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Branche> findBrancheEntities() {
        return findBrancheEntities(true, -1, -1);
    }

    public List<Branche> findBrancheEntities(int maxResults, int firstResult) {
        return findBrancheEntities(false, maxResults, firstResult);
    }

    private List<Branche> findBrancheEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Branche.class));
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

    public Branche findBranche(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Branche.class, id);
        } finally {
            em.close();
        }
    }

    public int getBrancheCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Branche> rt = cq.from(Branche.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
