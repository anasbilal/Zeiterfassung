/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.Mitarbeiter;
import com.iadvise.entities.User;

/**
 *
 * @author Teftazani1
 */
public class MitarbeiterJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4014139573257133281L;

	public MitarbeiterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Mitarbeiter create(Mitarbeiter mitarbeiter) {
        if (mitarbeiter.getUsersCollection() == null) {
            mitarbeiter.setUsersCollection(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<User> attachedUsersCollection = new ArrayList<User>();
            for (User usersCollectionUsersToAttach : mitarbeiter.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getUserId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            mitarbeiter.setUsersCollection(attachedUsersCollection);
            em.persist(mitarbeiter);
            for (User usersCollectionUsers : mitarbeiter.getUsersCollection()) {
                Mitarbeiter oldMitidOfUsersCollectionUsers = usersCollectionUsers.getMitID();
                usersCollectionUsers.setMitID(mitarbeiter);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldMitidOfUsersCollectionUsers != null) {
                    oldMitidOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldMitidOfUsersCollectionUsers = em.merge(oldMitidOfUsersCollectionUsers);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return mitarbeiter;
    }

    public void edit(Mitarbeiter mitarbeiter) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitarbeiter persistentMitarbeiter = em.find(Mitarbeiter.class, mitarbeiter.getMitid());
            Collection<User> usersCollectionOld = persistentMitarbeiter.getUsersCollection();
            Collection<User> usersCollectionNew = mitarbeiter.getUsersCollection();
            Collection<User> attachedUsersCollectionNew = new ArrayList<User>();
            for (User usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getUserId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            mitarbeiter.setUsersCollection(usersCollectionNew);
            mitarbeiter = em.merge(mitarbeiter);
            for (User usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    usersCollectionOldUsers.setMitID(null);
                    usersCollectionOldUsers = em.merge(usersCollectionOldUsers);
                }
            }
            for (User usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    Mitarbeiter oldMitidOfUsersCollectionNewUsers = usersCollectionNewUsers.getMitID();
                    usersCollectionNewUsers.setMitID(mitarbeiter);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldMitidOfUsersCollectionNewUsers != null && !oldMitidOfUsersCollectionNewUsers.equals(mitarbeiter)) {
                        oldMitidOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldMitidOfUsersCollectionNewUsers = em.merge(oldMitidOfUsersCollectionNewUsers);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mitarbeiter.getMitid();
                if (findMitarbeiter(id) == null) {
                    throw new NonexistentEntityException("The mitarbeiter with id " + id + " no longer exists.");
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
            Mitarbeiter mitarbeiter;
            try {
                mitarbeiter = em.getReference(Mitarbeiter.class, id);
                mitarbeiter.getMitid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mitarbeiter with id " + id + " no longer exists.", enfe);
            }
            Collection<User> usersCollection = mitarbeiter.getUsersCollection();
            for (User usersCollectionUsers : usersCollection) {
                usersCollectionUsers.setMitID(null);
                usersCollectionUsers = em.merge(usersCollectionUsers);
            }
            em.remove(mitarbeiter);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mitarbeiter> findMitarbeiterEntities() {
        return findMitarbeiterEntities(true, -1, -1);
    }

    public List<Mitarbeiter> findMitarbeiterEntities(int maxResults, int firstResult) {
        return findMitarbeiterEntities(false, maxResults, firstResult);
    }

    private List<Mitarbeiter> findMitarbeiterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mitarbeiter.class));
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

    public Mitarbeiter findMitarbeiter(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mitarbeiter.class, id);
        } finally {
            em.close();
        }
    }

    public int getMitarbeiterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mitarbeiter> rt = cq.from(Mitarbeiter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

	public Mitarbeiter findMitarbeiterByName(String vorname,String nachname) {
		 EntityManager em = getEntityManager();
		 Mitarbeiter mit = null;
		 Query query = em.createNamedQuery("Mitarbeiter.findByVor/nachname");
		 query.setParameter("vorname", vorname);
		 query.setParameter("nachname", nachname);
		 List<Mitarbeiter> list = query.getResultList( );
	      for( Mitarbeiter m:list )
	      {
	         System.out.print("Mitarbeiter ID :"+m.getMitid());
	         mit = m;
	      }
			
		return mit;
	}
    
}
