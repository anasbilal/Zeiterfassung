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
import com.iadvise.daoImpl.ProjektDao;
import com.iadvise.entities.Projekt;
@Path("/projekt")
public class ProjektResource {
	
	ProjektDao dao = new ProjektDao();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Projekt> findAll() {
		System.out.println("findAll");
		return dao.getAll();
	}

	
	 @GET @Path("search/{projektname}")
	 @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	 public Projekt findByName(@PathParam("projektname") String projektname) {
	 System.out.println("findByName: " + projektname); return dao.findByName(projektname);
	 }
	

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Projekt findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
		return dao.findById(Integer.parseInt(id));
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Projekt create(Projekt Projekt) throws Exception {
		System.out.println("creating Projekt");
		return dao.add(Projekt);
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Projekt update(Projekt Projekt) throws Exception {
		System.out.println("Updating Projekt: " + Projekt.getProjektname());
		dao.edit(Projekt);
		return Projekt;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id)
			throws NonexistentEntityException, IllegalOrphanException {
		dao.remove(id);
	}


}
