package cororok.dq;

import cororok.dq.util.LinkedNode;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class LinkedQuery extends Query implements
		LinkedNode<String, LinkedQuery> {

	LinkedQuery prev;
	LinkedQuery next;

	LinkedQuery(String newId) {
		super(newId);
	}

	@Override
	public void setNext(LinkedQuery next) {
		this.next = next;
	}

	@Override
	public LinkedQuery getNext() {
		return this.next;
	}

	@Override
	public void setPrevios(LinkedQuery previous) {
		this.prev = previous;
	}

	@Override
	public LinkedQuery getPrevios() {
		return this.prev;
	}

	@Override
	public String getKey() {
		return id;
	}

}
