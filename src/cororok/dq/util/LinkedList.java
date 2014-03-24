package cororok.dq.util;

public class LinkedList<E extends LinkedNode<?, E>> {

	E head;
	E tail;

	public void addHead(E e) {
		if (head == null) {
			head = e;
			tail = e;

			unlink(e);
		} else {
			appendToHead(e);
		}
	}

	private void appendToHead(E e) {
		head.setPrevios(e);
		e.setNext(head);

		e.setPrevios(null);
		head = e;
	}

	public boolean contains(E e) {
		E node = head;
		while (node != null) {
			if (node == e)
				return true;
			node = node.getNext();
		}
		return false;
	}

	public E getHead() {
		return tail;
	}

	public E getTail() {
		return tail;
	}

	public String getValues() {
		StringBuilder sb = new StringBuilder();
		E node = head;
		while (node != null) {
			sb.append(node.getKey());
			sb.append(',');
			node = node.getNext();
		}

		return sb.toString();
	}

	public void moveToFirst(E e) {
		if (e == head)
			return;

		if (e == tail) {
			tail = tail.getPrevios();
			tail.setNext(null);
		} else {
			unlinkInternal(e);
		}
		appendToHead(e);
	}

	private void unlinkInternal(E e) {
		E prev = e.getPrevios();
		E next = e.getNext();

		prev.setNext(next);
		next.setPrevios(prev);
	}

	private void unlink(E e) {
		e.setNext(null);
		e.setPrevios(null);
	}

	public void removeNode(E e) {
		if (e == head) {
			if (e == tail) {
				head = null;
				tail = null;
			} else {
				head = e.getNext();
				head.setPrevios(null);

				e.setNext(null);
			}
		} else if (e == tail) {
			tail = tail.getPrevios();
			tail.setNext(null);

			e.setPrevios(null);
		} else {
			unlinkInternal(e);
			unlink(e);
		}
	}

	/**
	 * it is called when there is the head
	 * 
	 * @return
	 */
	public E removeTail() {
		E oldTail = tail;

		tail = oldTail.getPrevios();
		tail.setNext(null);
		oldTail.setPrevios(null);

		return oldTail;
	}

	public void clear() {
		head = null;
		tail = null;
	}

}
