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
import com.iadvise.daoImpl.UserDao;
import com.iadvise.entities.User;
@Path("/user")
public class UserResource {

	UserDao dao = new UserDao();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<User> findAll() {
		System.out.println("findAll");
		return dao.getAll();
	}

	
	 @GET @Path("search/{query}")
	 @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	 public User findByName(@PathParam("query") String query) {
	 System.out.println("findByName: " + query); 
	 return dao.findByUsername(query);
	 }
	 

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
		return dao.findById(Integer.parseInt(id));
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User create(User user) throws Exception {
		System.out.println("creating User");
		return dao.add(user);
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User update(User user) throws Exception {
		System.out.println("Updating User: " + user.getUsername());
		dao.edit(user);
		return user;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id)
			throws NonexistentEntityException, IllegalOrphanException {
		dao.remove(id);
	}

}
