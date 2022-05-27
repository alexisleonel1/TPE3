package Repositorio;

import java.util.List;

import Entidades.Ciudad;
import Entidades.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class EstudianteRepositorio implements Repository<Estudiante>{
	
	private EntityManagerFactory emf;

	public EstudianteRepositorio(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public Estudiante insert(String nombre,String apellido, int edad, char genero,int dni,int numLibretaUniversitaria,Ciudad ciudad) {
		Estudiante e = new Estudiante(nombre,apellido,edad,genero,dni,numLibretaUniversitaria,ciudad);
		return this.save(e);
	}
	
	public Estudiante getByNumLibretaUniversitaria(int l) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e WHERE e.numLibretaUniversitaria = ?1", Estudiante.class);
		query.setParameter(1, l);
		Estudiante e = query.getSingleResult();
		em.close();
		return e;
	}
	
	public List<Estudiante> getEstudiantesByEdad() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e ORDER BY e.edad ASC", Estudiante.class);
		List<Estudiante> estudiantes = query.getResultList();
		em.close();
		return estudiantes;
	}
	
	public List<Estudiante> getEstudiantesByGenero(char g) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e WHERE e.genero = ?1", Estudiante.class);
		query.setParameter(1, g);
		List<Estudiante> estudiantes = query.getResultList();
		em.close();
		return estudiantes;
	}
	
	public List<Estudiante> getEstudiantesByCiudad(String c) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e JOIN e.ciudad c WHERE c.nombre = :ciudad", Estudiante.class);
		query.setParameter("ciudad", c);
		List<Estudiante> estudiantes = query.getResultList();
		em.close();
		return estudiantes;
	}

	@Override
	public Estudiante save(Estudiante e) {
		EntityManager em = emf.createEntityManager();
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
	public boolean delete(Estudiante e) {
		boolean remove = false;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(e)) {
			em.remove(e);
			remove = true;
			em.getTransaction().commit();
		}
		em.close();
		return remove;
	}

	@Override
	public Estudiante update(Estudiante e) {
		Estudiante updated = new Estudiante();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(e)) {
			updated = em.merge(e);
			em.getTransaction().commit();
		}
		em.close();
		return updated;
	}

}
