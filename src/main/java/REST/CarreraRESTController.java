package REST;

import Entidades.Carrera;
import Repositorio.CarreraRepositorio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/carreras")
public class CarreraRESTController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCarrera(Carrera carrera) {
		Carrera result = CarreraRepositorio.getInstance().save(carrera);
		if(result==null) {
			throw new RecursoDuplicado(carrera.getId());
		}else {
			return Response.status(201).entity(carrera).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Carrera getCarreraById(@PathParam("id") int ID) {
		Carrera carrera = CarreraRepositorio.getInstance().get(ID);
		if(carrera != null)
			return carrera;
		else
			throw new RecursoNoExiste(ID);
	}
	
	private class RecursoDuplicado extends WebApplicationException {
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
