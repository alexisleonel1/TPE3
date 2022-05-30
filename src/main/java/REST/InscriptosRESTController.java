package REST;

import java.text.ParseException;
import java.util.List;

import DTO.CarrerasInscriptosDTO;
import DTO.InformacionCarrerasDTO;
import Entidades.Estudiante;
import Entidades.Inscriptos;
import Repositorio.InscriptosRepositorio;
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

@Path("/inscriptos")
public class InscriptosRESTController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createInscripto(Inscriptos i) {
		Inscriptos result = InscriptosRepositorio.getInstance().save(i);
		if(result==null) {
			throw new RecursoDuplicado(0);
		}else {
			return Response.status(201).entity(i).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Inscriptos getInspritoById(@PathParam("id") int ID) {
		Inscriptos result = InscriptosRepositorio.getInstance().get(ID);
		if(result != null)
			return result;
		else
			throw new RecursoNoExiste(ID);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Inscriptos> getInscriptos() {
		List<Inscriptos> result = InscriptosRepositorio.getInstance().getAll();
		if(result != null)
			return result;
		else
			throw new RecursoNoExiste(0);
	}
	
	@GET
	@Path("/carreras_con_inscriptos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CarrerasInscriptosDTO> getCarrerasConInscriptos() {
		List<CarrerasInscriptosDTO> result = InscriptosRepositorio.getInstance().carrerasConInscriptos();
		if(result != null)
			return result;
		else
			throw new RecursoNoExiste(0);
	}
	
	@GET
	@Path("/estudiante_por_carrera")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Estudiante> estudiantePorCarrera(@QueryParam("carrera") String carrera,@QueryParam("ciudad") String ciudad) {
		List<Estudiante> result = InscriptosRepositorio.getInstance().estudiantePorCarrera(carrera,ciudad);
		if(result != null)
			return result;
		else
			throw new RecursoNoExiste(0);
	}
	
	@GET
	@Path("/info_carrera")
	@Produces(MediaType.APPLICATION_JSON)
	public List<InformacionCarrerasDTO> getInfoCarrera(@QueryParam("date") String date) throws ParseException {
		List<InformacionCarrerasDTO> result = InscriptosRepositorio.getInstance().getInfoCarrera(date);
		if(result != null)
			return result;
		else
			throw new RecursoNoExiste(0);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteInscripto(@PathParam("id") int id) {
		Inscriptos ciudad = InscriptosRepositorio.getInstance().get(id);
		if(ciudad!=null) {
			InscriptosRepositorio.getInstance().delete(id);
			return Response.ok().build();
			}
		else
			return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCiudad(Inscriptos ciudad) {
		Inscriptos result = InscriptosRepositorio.getInstance().update(ciudad);
		if(result==null) {
			throw new RecursoNoExiste(0);
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
