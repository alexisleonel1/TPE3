package Repositorio;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import DTO.CarrerasInscriptosDTO;
import DTO.InformacionCarrerasDTO;
import Entidades.Carrera;
import Entidades.Estudiante;
import Entidades.Inscriptos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class InscriptosRepositorio implements Repository<Inscriptos>{

	private EntityManagerFactory emf;

	public InscriptosRepositorio(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Inscriptos matricular(Estudiante e, Carrera c, String antiguedad, boolean graduado) throws ParseException {
		Timestamp dateTransform = this.transformDate(antiguedad);
		Inscriptos ec = new Inscriptos(e,c,dateTransform,graduado);
		return this.save(ec);
	}
	
	public List<CarrerasInscriptosDTO> carrerasConInscriptos() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<CarrerasInscriptosDTO> query = em.createQuery("SELECT new DTO.CarrerasInscriptosDTO( c.nombre ) FROM Inscriptos ec JOIN ec.carrera c GROUP BY ec.carrera ORDER BY count(ec.carrera) DESC", CarrerasInscriptosDTO.class);
		List<CarrerasInscriptosDTO> ci = query.getResultList();
		em.close();
		return ci;
	}
	
	public List<Estudiante> estudiantePorCarrera(String carrera,String ciudad) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Inscriptos i JOIN i.carrera c JOIN i.estudiante e JOIN e.ciudad ci WHERE c.nombre = :carrera AND ci.nombre = :ciudad", Estudiante.class);
		query.setParameter("carrera", carrera);
		query.setParameter("ciudad", ciudad);
		List<Estudiante> e = query.getResultList();
		em.close();
		return e;
	}
	
	public List<InformacionCarrerasDTO> getInfoCarrera(String date) throws ParseException {
		Timestamp dateTransform = transformDate(date);
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<InformacionCarrerasDTO> query = em.createQuery("SELECT new DTO.InformacionCarrerasDTO( c.nombre,e.nombre,e.apellido,i.graduado,DATEDIFF(:date, i.antiguedad) AS antiguedad ) FROM Inscriptos i JOIN i.estudiante e JOIN i.carrera c ORDER BY i.carrera, i.antiguedad DESC", InformacionCarrerasDTO.class);
		query.setParameter("date", dateTransform);
		List<InformacionCarrerasDTO> e = query.getResultList();
		em.getTransaction().commit();
		em.close();
		return e;
	}
	
	private Timestamp transformDate(String date) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date d = dateFormat.parse(date);
		long time = d.getTime();
		Timestamp dateTransform = new Timestamp(time);
		return dateTransform;
	}
	
	@Override
	public Inscriptos save(Inscriptos i) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(i)) {
			em.persist(i);
		}else {
			em.merge(i);
		}
		em.getTransaction().commit();
		em.close();
		return i;
	}

	@Override
	public boolean delete(Inscriptos i) {
		boolean remove = false;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(i)) {
			em.remove(i);
			remove = true;
			em.getTransaction().commit();
		}
		em.close();
		return remove;
	}

	@Override
	public Inscriptos update(Inscriptos i) {
		Inscriptos updated = new Inscriptos();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(i)) {
			updated = em.merge(i);
			em.getTransaction().commit();
		}
		em.close();
		return updated;
	}
	
}