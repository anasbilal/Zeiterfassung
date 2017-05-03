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
import com.iadvise.daoImpl.KampagneDao;
import com.iadvise.entities.Kampagne;
@Path("/kampagne")
public class KampagneResource {
	
	KampagneDao dao = new KampagneDao();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Kampagne> findAll() {
		System.out.println("findAll");
		return dao.getAll();
	}

	/*
	 * @GET @Path("search/{query}")
	 * 
	 * @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	 * public List<Kampagne> findByName(@PathParam("query") String query) {
	 * System.out.println("findByName: " + query); return dao.findByName(query);
	 * }
	 */

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Kampagne findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
		return dao.findById(Integer.parseInt(id));
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Kampagne create(Kampagne Kampagne) throws Exception {
		System.out.println("creating Kampagne");
		return dao.add(Kampagne);
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Kampagne update(Kampagne Kampagne) throws Exception {
		System.out.println("Updating Kampagne: " + Kampagne.getBeschreibung());
		dao.edit(Kampagne);
		return Kampagne;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id)
			throws NonexistentEntityException, IllegalOrphanException {
		dao.remove(id);
	}


}
