package cororok.dq.util;

import java.util.Iterator;

/**
 * @author songduk.park cororok@gmail.com
 *
 * @param <E>
 */
public class ArrayIterator<E> implements Iterator<E> {

	private E[] ts;
	private int position = -1;
	private int lastIndex;

	public ArrayIterator(E[] ts) {
		this.ts = ts;
		this.lastIndex = ts.length - 1;

	}

	@Override
	public boolean hasNext() {
		return position < lastIndex;
	}

	@Override
	public E next() {
		return ts[++position];
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
