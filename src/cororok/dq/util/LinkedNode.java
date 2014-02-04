package cororok.dq.util;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public interface LinkedNode<K> {

	public void setNext(LinkedNode<K> next);

	public LinkedNode<K> getNext();

	public void setPrevios(LinkedNode<K> previous);

	public LinkedNode<K> getPrevios();

	public K getKey();

}
