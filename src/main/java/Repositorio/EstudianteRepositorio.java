package Repositorio;

import java.util.List;

import Entidades.Estudiante;
import Factory.Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class EstudianteRepositorio implements Repository<Estudiante>{
	
	private static EstudianteRepositorio estudianteRepo;
	
	public static EstudianteRepositorio getInstance() {
		if(estudianteRepo==null)
			estudianteRepo=new EstudianteRepositorio();
		return estudianteRepo;
	}
	
	public EstudianteRepositorio() {}
	
	public Estudiante getByNumLibretaUniversitaria(int l) {
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e WHERE e.numLibretaUniversitaria = ?1", Estudiante.class);
		query.setParameter(1, l);
		Estudiante e = query.getSingleResult();
		em.close();
		return e;
	}
	
	public List<Estudiante> getEstudiantesByEdad() {
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e ORDER BY e.edad ASC", Estudiante.class);
		List<Estudiante> estudiantes = query.getResultList();
		em.close();
		return estudiantes;
	}
	
	public List<Estudiante> getEstudiantesByGenero(char g) {
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e WHERE e.genero = ?1", Estudiante.class);
		query.setParameter(1, g);
		List<Estudiante> estudiantes = query.getResultList();
		em.close();
		return estudiantes;
	}
	
	public List<Estudiante> getEstudiantesByCiudad(String c) {
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e JOIN e.ciudad c WHERE c.nombre = :ciudad", Estudiante.class);
		query.setParameter("ciudad", c);
		List<Estudiante> estudiantes = query.getResultList();
		em.close();
		return estudiantes;
	}

	@Override
	public Estudiante save(Estudiante e) {
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(e)) {
			em.persist(e);
		}else {
			em.merge(e);
		}
		em.getTransaction().commit();
		em.close();
		return e;
	}

	@Override
	public boolean delete(int ID) {
		boolean result=false;
		EntityManager entityManager = Factory.createEntityManager();
		entityManager.getTransaction().begin();
		Estudiante estudiante=entityManager.find(Estudiante.class, ID);
		if (estudiante!=null) result=true;
		entityManager.remove(estudiante);
		entityManager.getTransaction().commit();
		entityManager.close();
		return result;
	}

	@Override
	public Estudiante update(Estudiante e) {
		Estudiante updated = new Estudiante();
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(e)) {
			updated = em.merge(e);
			em.getTransaction().commit();
		}
		em.close();
		return updated;
	}

	@Override
	public Estudiante get(int ID) {
		EntityManager entityManager = Factory.createEntityManager();
		Estudiante estudiante = entityManager.find(Estudiante.class, ID);
		entityManager.close();
		return estudiante;
	}

	@Override
	public List<Estudiante> getAll() {
		EntityManager entityManager = Factory.createEntityManager();
		entityManager.getTransaction().begin();
		CriteriaBuilder  cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Estudiante> cq = cb.createQuery(Estudiante.class);
        Root<Estudiante> rootEntry = cq.from(Estudiante.class);
        CriteriaQuery<Estudiante> all = cq.select(rootEntry);
        TypedQuery<Estudiante> allQuery = entityManager.createQuery(all);
        entityManager.getTransaction().commit();
        List<Estudiante> result = allQuery.getResultList();
		entityManager.close();
        return result;
	}

}
