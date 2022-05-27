package Repositorio;

import Entidades.Ciudad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CiudadRepositorio implements Repository<Ciudad>{
	
	private EntityManagerFactory emf;

	public CiudadRepositorio(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public Ciudad insert(String nombre) {
		Ciudad ci = new Ciudad(nombre);
		return this.save(ci);
	}
	
	@Override
	public Ciudad save(Ciudad c) {
		EntityManager em = emf.createEntityManager();
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
	public boolean delete(Ciudad c) {
		boolean remove = false;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(c)) {
			em.remove(c);
			remove = true;
			em.getTransaction().commit();
		}
		em.close();
		return remove;
	}

	@Override
	public Ciudad update(Ciudad c) {
		Ciudad updated = new Ciudad();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(!em.contains(c)) {
			updated = em.merge(c);
			em.getTransaction().commit();
		}
		em.close();
		return updated;
	}

}
