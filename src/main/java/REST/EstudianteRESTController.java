package REST;

import java.util.List;

import Entidades.Estudiante;
import Repositorio.EstudianteRepositorio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/estudiantes")
public class EstudianteRESTController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEstudiante(Estudiante estudiante) {
		Estudiante result = EstudianteRepositorio.getInstance().save(estudiante);
		if(result==null) {
			throw new RecursoDuplicado(estudiante.getId());
		}else {
			return Response.status(201).entity(estudiante).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Estudiante getEstudianteById(@PathParam("id") int ID) {
		Estudiante estudiante = EstudianteRepositorio.getInstance().get(ID);
		if(estudiante != null)
			return estudiante;
		else
			throw new RecursoNoExiste(ID);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Estudiante> getEstudiantes() {
		List<Estudiante> estudiante = EstudianteRepositorio.getInstance().getAll();
		if(estudiante != null)
			return estudiante;
		else
			throw new RecursoNoExiste(0);
	}
	
	@GET
	@Path("/estudiantesByEdad")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Estudiante> getByEdad() {
		List<Estudiante> result= EstudianteRepositorio.getInstance().getEstudiantesByEdad();
		return result;
	}
	
	@GET
	@Path("/libreta")
	@Produces(MediaType.APPLICATION_JSON)
	public Estudiante getByNumLibretaUniversitaria(@QueryParam("from") int from) {
		Estudiante result= EstudianteRepositorio.getInstance().getByNumLibretaUniversitaria(from);
		return result;
	}
	
	@GET
	@Path("/genero")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Estudiante> getEstudiantesByGenero(@QueryParam("genero") char from) {
		List<Estudiante> result= EstudianteRepositorio.getInstance().getEstudiantesByGenero(from);
		return result;
	}
	
	
	@GET
	@Path("/ciudad")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Estudiante> getEstudiantesByCiudad(@QueryParam("ciudad") String from) {
		List<Estudiante> result= EstudianteRepositorio.getInstance().getEstudiantesByCiudad(from);
		return result;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEstudiante(@PathParam("id") int id) {
		Estudiante estudiante = EstudianteRepositorio.getInstance().get(id);
		if(estudiante!=null) {
			EstudianteRepositorio.getInstance().delete(id);
			return Response.ok().build();
			}
		else
			return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEstudiante(Estudiante estudiante) {
		Estudiante result = EstudianteRepositorio.getInstance().update(estudiante);
		if(result==null) {
			throw new RecursoNoExiste(estudiante.getId());
		}else {
			return Response.status(200).entity(estudiante).build();
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
