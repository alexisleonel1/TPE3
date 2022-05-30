package REST;

import java.util.List;

import Entidades.Ciudad;
import Repositorio.CiudadRepositorio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ciudades")
public class CiudadRESTController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCiudad(Ciudad ciudad) {
		Ciudad result = CiudadRepositorio.getInstance().save(ciudad);
		if(result==null) {
			throw new RecursoDuplicado(ciudad.getId());
		}else {
			return Response.status(201).entity(ciudad).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Ciudad getCidudadById(@PathParam("id") int ID) {
		Ciudad ciudad = CiudadRepositorio.getInstance().get(ID);
		if(ciudad != null)
			return ciudad;
		else
			throw new RecursoNoExiste(ID);
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ciudad> getCiudades() {
		List<Ciudad> ciudad = CiudadRepositorio.getInstance().getAll();
		if(ciudad != null)
			return ciudad;
		else
			throw new RecursoNoExiste(0);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCiudad(@PathParam("id") int id) {
		Ciudad ciudad = CiudadRepositorio.getInstance().get(id);
		if(ciudad!=null) {
			CiudadRepositorio.getInstance().delete(id);
			return Response.ok().build();
			}
		else
			return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCiudad(Ciudad ciudad) {
		Ciudad result = CiudadRepositorio.getInstance().update(ciudad);
		if(result==null) {
			throw new RecursoNoExiste(ciudad.getId());
		}else {
				return Response.status(200).entity(ciudad).build();
			}
		}
	
	public class RecursoDuplicado extends WebApplicationException {
	     public RecursoDuplicado(int id) {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso con ID "+id+" ya existe").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	
	public class RecursoNoExiste extends WebApplicationException {
	     public RecursoNoExiste(int id) {
	         super(Response.status(Response.Status.NOT_FOUND)
	             .entity("El recurso con id "+id+" no fue encontrado").type(MediaType.TEXT_PLAIN).build());
	     }
	}

}
