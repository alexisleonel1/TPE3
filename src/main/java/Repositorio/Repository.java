package Repositorio;

import java.util.List;

public interface Repository<T> {

	T save(T t);
	boolean delete(int ID);
	T update(T t);
	T get(int ID);
	List<T> getAll();
}