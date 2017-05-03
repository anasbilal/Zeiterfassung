package com.iadvise.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iadvise.controller.exceptions.IllegalOrphanException;
import com.iadvise.controller.exceptions.NonexistentEntityException;
import com.iadvise.daoImpl.AktivitaetDao;
import com.iadvise.entities.Aktivitaet;

@Path("/aktivitaet")
public class AktivitaetResource {
	
	AktivitaetDao dao = new AktivitaetDao();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Aktivitaet> findAll() {
		System.out.println("findAll");
		return dao.getAll();
	}

	/*
	 * @GET @Path("search/{query}")
	 * 
	 * @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	 * public List<Aktivitaet> findByName(@PathParam("query") String query) {
	 * System.out.println("findByName: " + query); return dao.findByName(query);
	 * }
	 */

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Aktivitaet findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
		return dao.findById(Integer.parseInt(id));
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Aktivitaet create(Aktivitaet Aktivitaet) throws Exception {
		System.out.println("creating Aktivitaet");
		return dao.add(Aktivitaet);
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Aktivitaet update(Aktivitaet Aktivitaet) throws Exception {
		System.out.println("Updating Aktivitaet: " + Aktivitaet.getBeschreibung());
		dao.edit(Aktivitaet);
		return Aktivitaet;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id)
			throws NonexistentEntityException, IllegalOrphanException {
		dao.remove(id);
	}


}
