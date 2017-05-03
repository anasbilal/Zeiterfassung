/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iadvise.controller;

import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.entities.UserRole;

import java.io.Serializable;

import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.iadvise.entities.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Teftazani1
 */
public class UserRolesJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4798389013779749805L;

	public UserRolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UserRole create(UserRole userRoles) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = userRoles.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                userRoles.setUserId(userId);
            }
            em.persist(userRoles);
            if (userId != null) {
                userId.getUserRolesCollection().add(userRoles);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return userRoles;
    }

    public void edit(UserRole userRoles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserRole persistentUserRoles = em.find(UserRole.class, userRoles.getUserRoleId());
            User userIdOld = persistentUserRoles.getUserId();
            User userIdNew = userRoles.getUserId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                userRoles.setUserId(userIdNew);
            }
            userRoles = em.merge(userRoles);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getUserRolesCollection().remove(userRoles);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getUserRolesCollection().add(userRoles);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userRoles.getUserRoleId();
                if (findUserRoles(id) == null) {
                    throw new NonexistentEntityException("The userRoles with id " + id + " no longer exists.");
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
            UserRole userRoles;
            try {
                userRoles = em.getReference(UserRole.class, id);
                userRoles.getUserRoleId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userRoles with id " + id + " no longer exists.", enfe);
            }
            User userId = userRoles.getUserId();
            if (userId != null) {
                userId.getUserRolesCollection().remove(userRoles);
                userId = em.merge(userId);
            }
            em.remove(userRoles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserRole> findUserRolesEntities() {
        return findUserRolesEntities(true, -1, -1);
    }

    public List<UserRole> findUserRolesEntities(int maxResults, int firstResult) {
        return findUserRolesEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List<UserRole> findUserRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserRole.class));
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

    public UserRole findUserRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserRole.class, id);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public int getUserRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserRole> rt = cq.from(UserRole.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
