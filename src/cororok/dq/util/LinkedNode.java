package cororok.dq.util;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public interface LinkedNode<K, E extends LinkedNode<K, E>> {

	public void setNext(E next);

	public E getNext();

	public void setPrevios(E previous);

	public E getPrevios();

	public K getKey();
	

}
