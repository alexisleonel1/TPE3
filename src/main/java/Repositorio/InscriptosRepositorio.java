package Repositorio;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import DTO.CarrerasInscriptosDTO;
import DTO.InformacionCarrerasDTO;
import Entidades.Estudiante;
import Entidades.Inscriptos;
import Factory.Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class InscriptosRepositorio implements Repository<Inscriptos>{

	private static InscriptosRepositorio inscriptosteRepo;
	
	public static InscriptosRepositorio getInstance() {
		if(inscriptosteRepo==null)
			inscriptosteRepo=new InscriptosRepositorio();
		return inscriptosteRepo;
	}
	
	public InscriptosRepositorio() {}
	
	public List<CarrerasInscriptosDTO> carrerasConInscriptos() {
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<CarrerasInscriptosDTO> query = em.createQuery("SELECT new DTO.CarrerasInscriptosDTO( c.nombre ) FROM Inscriptos ec JOIN ec.carrera c GROUP BY ec.carrera ORDER BY count(ec.carrera) DESC", CarrerasInscriptosDTO.class);
		List<CarrerasInscriptosDTO> ci = query.getResultList();
		em.close();
		return ci;
	}
	
	public List<Estudiante> estudiantePorCarrera(String carrera,String ciudad) {
		EntityManager em = Factory.createEntityManager();
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
		
		EntityManager em = Factory.createEntityManager();
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
		EntityManager em = Factory.createEntityManager();
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
	public boolean delete(int ID) {
		boolean result=false;
		EntityManager entityManager = Factory.createEntityManager();
		entityManager.getTransaction().begin();
		Inscriptos inscripto = entityManager.find(Inscriptos.class, ID);
		if (inscripto!=null) result=true;
		entityManager.remove(inscripto);
		entityManager.getTransaction().commit();
		entityManager.close();
		return result;
	}

	@Override
	public Inscriptos update(Inscriptos i) {
		Inscriptos updated = new Inscriptos();
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(i)) {
			updated = em.merge(i);
			em.getTransaction().commit();
		}
		em.close();
		return updated;
	}

	@Override
	public Inscriptos get(int IDestudiante) {
		EntityManager entityManager = Factory.createEntityManager();
		TypedQuery<Inscriptos> query = entityManager.createQuery("SELECT * FROM Inscriptos WHERE estudiante = :IDestudiante", Inscriptos.class);
		query.setParameter("IDestudiante", IDestudiante);
		Inscriptos inscripto = query.getSingleResult();
		entityManager.close();
		return inscripto;
	}

	@Override
	public List<Inscriptos> getAll() {
		EntityManager entityManager = Factory.createEntityManager();
		entityManager.getTransaction().begin();
		CriteriaBuilder  cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Inscriptos> cq = cb.createQuery(Inscriptos.class);
        Root<Inscriptos> rootEntry = cq.from(Inscriptos.class);
        CriteriaQuery<Inscriptos> all = cq.select(rootEntry);
        TypedQuery<Inscriptos> allQuery = entityManager.createQuery(all);
        entityManager.getTransaction().commit();
        List<Inscriptos> result = allQuery.getResultList();
		entityManager.close();
        return result;
	}
	
}