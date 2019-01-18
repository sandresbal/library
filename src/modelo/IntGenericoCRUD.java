package modelo;

import java.util.List;

public interface IntGenericoCRUD<E, K> {

	public int insert(E entidad);
	public List<E> findAll();
	public E findById(K key);
	public int update(E entidad);
	public int delete(K key);
	
}
