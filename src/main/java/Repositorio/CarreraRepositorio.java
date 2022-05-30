package Repositorio;

import java.util.List;

import Entidades.Carrera;
import Factory.Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CarreraRepositorio implements Repository<Carrera>{
	
	private static CarreraRepositorio carreraRepo;
	
	public static CarreraRepositorio getInstance() {
		if(carreraRepo==null)
			carreraRepo=new CarreraRepositorio();
		return carreraRepo;
	}
	
	public CarreraRepositorio() {}

	@Override
	public Carrera save(Carrera c) {
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(c)) {
			em.persist(c);
		}else {
			em.merge(c);
		}
		em.getTransaction().commit();
		em.close();
		return c;
	}

	@Override
	public boolean delete(int ID) {
		boolean result=false;
		EntityManager entityManager = Factory.createEntityManager();
		entityManager.getTransaction().begin();
		Carrera carrera=entityManager.find(Carrera.class, ID);
		if (carrera!=null) result=true;
		entityManager.remove(carrera);
		entityManager.getTransaction().commit();
		entityManager.close();
		return result;
	}

	@Override
	public Carrera update(Carrera c) {
		Carrera updated = new Carrera();
		EntityManager em = Factory.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(c)) {
			updated = em.merge(c);
			em.getTransaction().commit();
		}
		em.close();
		return updated;
	}
	
	@Override
	public Carrera get(int ID) {
		EntityManager entityManager = Factory.createEntityManager();
		Carrera carrera = entityManager.find(Carrera.class, ID);
		entityManager.close();
		return carrera;
	}
	
	@Override
	public List<Carrera> getAll() {
		EntityManager entityManager = Factory.createEntityManager();
		entityManager.getTransaction().begin();
		CriteriaBuilder  cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Carrera> cq = cb.createQuery(Carrera.class);
        Root<Carrera> rootEntry = cq.from(Carrera.class);
        CriteriaQuery<Carrera> all = cq.select(rootEntry);
        TypedQuery<Carrera> allQuery = entityManager.createQuery(all);
        entityManager.getTransaction().commit();
        List<Carrera> result = allQuery.getResultList();
		entityManager.close();
        return result;
	}
	
}
