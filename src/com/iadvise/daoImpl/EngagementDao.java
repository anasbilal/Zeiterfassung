package com.iadvise.daoImpl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.iadvise.controller.EngagementJpaController;
import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.dao.EngagementInterface;
import com.iadvise.entities.Engagement;

public class EngagementDao implements EngagementInterface {
	
	private final EngagementJpaController engagementController;
    private final EntityManagerFactory emf;

	public EngagementDao() {
		emf = Persistence.createEntityManagerFactory("JPA-i-advise");
		engagementController = new EngagementJpaController(emf);
	}

	@Override
	public Engagement add(Engagement engagement) throws Exception {
		return engagementController.create(engagement);
		
	}

	@Override
	public void edit(Engagement engagement) throws Exception {
		engagementController.edit(engagement);
		
	}

	@Override
	public void remove(int ID) throws NonexistentEntityException,
			IllegalOrphanException {
		engagementController.destroy(ID);
		
	}

	@Override
	public List<Engagement> getAll() {
		
		return engagementController.findEngagementEntities();
	}

	@Override
	public Engagement findById(int ID) {
		
		return engagementController.findEngagement(ID);
	}

}
