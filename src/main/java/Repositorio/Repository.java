package Repositorio;

public interface Repository<T> {

	T save(T t);
	boolean delete(T t);
	T update(T t);
}
