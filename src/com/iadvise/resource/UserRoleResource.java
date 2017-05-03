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
import com.iadvise.daoImpl.UserRoleDao;
import com.iadvise.entities.UserRole;

@Path("/userRole")
public class UserRoleResource {
	
	UserRoleDao dao = new UserRoleDao();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<UserRole> findAll() {
		System.out.println("findAll");
		return dao.getAll();
	}

	/*
	 * @GET @Path("search/{query}")
	 * 
	 * @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	 * public List<UserRole> findByName(@PathParam("query") String query) {
	 * System.out.println("findByName: " + query); return dao.findByName(query);
	 * }
	 */

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserRole findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
		return dao.findById(Integer.parseInt(id));
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserRole create(UserRole UserRole) throws Exception {
		System.out.println("creating UserRole");
		return dao.add(UserRole);
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserRole update(UserRole UserRole) throws Exception {
		System.out.println("Updating UserRole: " + UserRole.getUserRoleId());
		dao.edit(UserRole);
		return UserRole;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id)
			throws NonexistentEntityException, IllegalOrphanException {
		dao.remove(id);
	}


}
