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
import com.iadvise.daoImpl.MitarbeiterDao;
import com.iadvise.entities.Mitarbeiter;
@Path("/mitarbeiter")
public class MitarbeiterResource {
	
	MitarbeiterDao dao = new MitarbeiterDao();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Mitarbeiter> findAll() {
		System.out.println("findAll");
		return dao.getAll();
	}

	
	 @GET @Path("search/{vorname}/{nachname}")
	 @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	 public Mitarbeiter findByName(@PathParam("vorname") String vorname,@PathParam("nachname") String nachname) {
	 System.out.println("findByName: " + vorname+nachname); return dao.findByName(vorname, nachname);
	 }
	 

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Mitarbeiter findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
		return dao.findById(Integer.parseInt(id));
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Mitarbeiter create(Mitarbeiter Mitarbeiter) throws Exception {
		System.out.println("creating Mitarbeiter");
		return dao.add(Mitarbeiter);
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Mitarbeiter update(Mitarbeiter Mitarbeiter) throws Exception {
		System.out.println("Updating Mitarbeiter: " + Mitarbeiter.getNachname());
		dao.edit(Mitarbeiter);
		return Mitarbeiter;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id)
			throws NonexistentEntityException, IllegalOrphanException {
		dao.remove(id);
	}


}
