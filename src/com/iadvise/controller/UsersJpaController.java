/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.controller;

import java.io.Serializable;
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
public class UsersJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1780195145135300231L;

	/**
	 * 
	 */
	

	public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User create(User users) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mitarbeiter mitid = users.getMitID();
            if (mitid != null) {
                mitid = em.getReference(mitid.getClass(), mitid.getMitid());
                users.setMitID(mitid);
            }
            em.persist(users);
            if (mitid != null) {
                mitid.getUsersCollection().add(users);
                mitid = em.merge(mitid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return users;
    }

    public void edit(User users) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUsers = em.find(User.class, users.getUserId());
            Mitarbeiter mitidOld = persistentUsers.getMitID();
            Mitarbeiter mitidNew = users.getMitID();
            if (mitidNew != null) {
                mitidNew = em.getReference(mitidNew.getClass(), mitidNew.getMitid());
                users.setMitID(mitidNew);
            }
            users = em.merge(users);
            if (mitidOld != null && !mitidOld.equals(mitidNew)) {
                mitidOld.getUsersCollection().remove(users);
                mitidOld = em.merge(mitidOld);
            }
            if (mitidNew != null && !mitidNew.equals(mitidOld)) {
                mitidNew.getUsersCollection().add(users);
                mitidNew = em.merge(mitidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = users.getUserId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            User users;
            try {
                users = em.getReference(User.class, id);
                users.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            Mitarbeiter mitid = users.getMitID();
            if (mitid != null) {
                mitid.getUsersCollection().remove(users);
                mitid = em.merge(mitid);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<User> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<User> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    

	public User findUserByUsername(String username) {
		 EntityManager em = getEntityManager();
		 Query query = em.createNamedQuery("Users.findByUsername");
		 query.setParameter("username", username);
		 List<User>  list = query.getResultList();
			      
		return list.get(0);
	}
    
}
