package cororok.dq;

import cororok.dq.util.LinkedNode;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class LinkedQuery extends Query implements LinkedNode<String> {

	LinkedNode<String> prev;
	LinkedNode<String> next;

	LinkedQuery(String newId) {
		super(newId);
	}

	@Override
	public void setNext(LinkedNode<String> next) {
		this.next = next;
	}

	@Override
	public LinkedNode<String> getNext() {
		return this.next;
	}

	@Override
	public void setPrevios(LinkedNode<String> previous) {
		this.prev = previous;
	}

	@Override
	public LinkedNode<String> getPrevios() {
		return this.prev;
	}

	@Override
	public String getKey() {
		return id;
	}

}
