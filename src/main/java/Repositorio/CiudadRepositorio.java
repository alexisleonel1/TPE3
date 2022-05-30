package Repositorio;

import java.util.List;

import Entidades.Ciudad;
import Factory.Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CiudadRepositorio implements Repository<Ciudad>{
	
private static CiudadRepositorio ciudadRepo;
	
	public static CiudadRepositorio getInstance() {
		if(ciudadRepo==null)
			ciudadRepo=new CiudadRepositorio();
		return ciudadRepo;
	}
	
	public CiudadRepositorio() {}
	
	@Override
	public Ciudad save(Ciudad c) {
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
		Ciudad ciudad=entityManager.find(Ciudad.class, ID);
		if (ciudad!=null) result=true;
		entityManager.remove(ciudad);
		entityManager.getTransaction().commit();
		entityManager.close();
		return result;
	}

	@Override
	public Ciudad update(Ciudad c) {
		Ciudad updated = new Ciudad();
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
	public Ciudad get(int ID) {
		EntityManager entityManager = Factory.createEntityManager();
		Ciudad ciudad = entityManager.find(Ciudad.class, ID);
		entityManager.close();
		return ciudad;
	}

	@Override
	public List<Ciudad> getAll() {
		EntityManager entityManager = Factory.createEntityManager();
		entityManager.getTransaction().begin();
		CriteriaBuilder  cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Ciudad> cq = cb.createQuery(Ciudad.class);
        Root<Ciudad> rootEntry = cq.from(Ciudad.class);
        CriteriaQuery<Ciudad> all = cq.select(rootEntry);
        TypedQuery<Ciudad> allQuery = entityManager.createQuery(all);
        entityManager.getTransaction().commit();
        List<Ciudad> result = allQuery.getResultList();
		entityManager.close();
        return result;
	}

}
