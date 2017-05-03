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
import com.iadvise.entities.Aktivitaet;
import com.iadvise.entities.Mitarbeiter;
import com.iadvise.entities.Firma;
import com.iadvise.entities.Kampagne;
import com.iadvise.entities.Kontakt;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Teftazani1
 */
public class KontaktJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5253960613413056234L;

	public KontaktJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Kontakt create(Kontakt kontakt) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aktivitaet aktid = kontakt.getAktid();
            if (aktid != null) {
                aktid = em.getReference(aktid.getClass(), aktid.getAktid());
                kontakt.setAktid(aktid);
            }
            Mitarbeiter mitid = kontakt.getMitid();
            if (mitid != null) {
                mitid = em.getReference(mitid.getClass(), mitid.getMitid());
                kontakt.setMitid(mitid);
            }
            Firma firmaid = kontakt.getFirmaid();
            if (firmaid != null) {
                firmaid = em.getReference(firmaid.getClass(), firmaid.getFirmaid());
                kontakt.setFirmaid(firmaid);
            }
            Kampagne kampid = kontakt.getKampid();
            if (kampid != null) {
                kampid = em.getReference(kampid.getClass(), kampid.getKampid());
                kontakt.setKampid(kampid);
            }
            em.persist(kontakt);
            if (aktid != null) {
                aktid.getKontaktCollection().add(kontakt);
                aktid = em.merge(aktid);
            }
            if (mitid != null) {
                mitid.getKontaktCollection().add(kontakt);
                mitid = em.merge(mitid);
            }
            if (firmaid != null) {
                firmaid.getKontaktCollection().add(kontakt);
                firmaid = em.merge(firmaid);
            }
            if (kampid != null) {
                kampid.getKontaktCollection().add(kontakt);
                kampid = em.merge(kampid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
		return kontakt;
    }

    public void edit(Kontakt kontakt) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kontakt persistentKontakt = em.find(Kontakt.class, kontakt.getKontaktid());
            Aktivitaet aktidOld = persistentKontakt.getAktid();
            Aktivitaet aktidNew = kontakt.getAktid();
            Mitarbeiter mitidOld = persistentKontakt.getMitid();
            Mitarbeiter mitidNew = kontakt.getMitid();
            Firma firmaidOld = persistentKontakt.getFirmaid();
            Firma firmaidNew = kontakt.getFirmaid();
            Kampagne kampidOld = persistentKontakt.getKampid();
            Kampagne kampidNew = kontakt.getKampid();
            if (aktidNew != null) {
                aktidNew = em.getReference(aktidNew.getClass(), aktidNew.getAktid());
                kontakt.setAktid(aktidNew);
            }
            if (mitidNew != null) {
                mitidNew = em.getReference(mitidNew.getClass(), mitidNew.getMitid());
                kontakt.setMitid(mitidNew);
            }
            if (firmaidNew != null) {
                firmaidNew = em.getReference(firmaidNew.getClass(), firmaidNew.getFirmaid());
                kontakt.setFirmaid(firmaidNew);
            }
            if (kampidNew != null) {
                kampidNew = em.getReference(kampidNew.getClass(), kampidNew.getKampid());
                kontakt.setKampid(kampidNew);
            }
            kontakt = em.merge(kontakt);
            if (aktidOld != null && !aktidOld.equals(aktidNew)) {
                aktidOld.getKontaktCollection().remove(kontakt);
                aktidOld = em.merge(aktidOld);
            }
            if (aktidNew != null && !aktidNew.equals(aktidOld)) {
                aktidNew.getKontaktCollection().add(kontakt);
                aktidNew = em.merge(aktidNew);
            }
            if (mitidOld != null && !mitidOld.equals(mitidNew)) {
                mitidOld.getKontaktCollection().remove(kontakt);
                mitidOld = em.merge(mitidOld);
            }
            if (mitidNew != null && !mitidNew.equals(mitidOld)) {
                mitidNew.getKontaktCollection().add(kontakt);
                mitidNew = em.merge(mitidNew);
            }
            if (firmaidOld != null && !firmaidOld.equals(firmaidNew)) {
                firmaidOld.getKontaktCollection().remove(kontakt);
                firmaidOld = em.merge(firmaidOld);
            }
            if (firmaidNew != null && !firmaidNew.equals(firmaidOld)) {
                firmaidNew.getKontaktCollection().add(kontakt);
                firmaidNew = em.merge(firmaidNew);
            }
            if (kampidOld != null && !kampidOld.equals(kampidNew)) {
                kampidOld.getKontaktCollection().remove(kontakt);
                kampidOld = em.merge(kampidOld);
            }
            if (kampidNew != null && !kampidNew.equals(kampidOld)) {
                kampidNew.getKontaktCollection().add(kontakt);
                kampidNew = em.merge(kampidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = kontakt.getKontaktid();
                if (findKontakt(id) == null) {
                    throw new NonexistentEntityException("The kontakt with id " + id + " no longer exists.");
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
            Kontakt kontakt;
            try {
                kontakt = em.getReference(Kontakt.class, id);
                kontakt.getKontaktid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kontakt with id " + id + " no longer exists.", enfe);
            }
            Aktivitaet aktid = kontakt.getAktid();
            if (aktid != null) {
                aktid.getKontaktCollection().remove(kontakt);
                aktid = em.merge(aktid);
            }
            Mitarbeiter mitid = kontakt.getMitid();
            if (mitid != null) {
                mitid.getKontaktCollection().remove(kontakt);
                mitid = em.merge(mitid);
            }
            Firma firmaid = kontakt.getFirmaid();
            if (firmaid != null) {
                firmaid.getKontaktCollection().remove(kontakt);
                firmaid = em.merge(firmaid);
            }
            Kampagne kampid = kontakt.getKampid();
            if (kampid != null) {
                kampid.getKontaktCollection().remove(kontakt);
                kampid = em.merge(kampid);
            }
            em.remove(kontakt);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kontakt> findKontaktEntities() {
        return findKontaktEntities(true, -1, -1);
    }

    public List<Kontakt> findKontaktEntities(int maxResults, int firstResult) {
        return findKontaktEntities(false, maxResults, firstResult);
    }

    private List<Kontakt> findKontaktEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kontakt.class));
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

    public Kontakt findKontakt(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kontakt.class, id);
        } finally {
            em.close();
        }
    }

    public int getKontaktCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kontakt> rt = cq.from(Kontakt.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
