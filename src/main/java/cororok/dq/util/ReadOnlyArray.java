package cororok.dq.util;

import java.util.Iterator;

/**
 * @author songduk.park cororok@gmail.com
 *
 * @param <E>
 */
public class ReadOnlyArray<E extends Comparable<E>> implements
		Comparable<ReadOnlyArray<E>>, Iterable<E> {

	private E[] es;

	public ReadOnlyArray(E[] es) {
		this.es = es;
	}

	public int size() {
		return es.length;
	}

	public E get(int index) {
		return es[index];
	}

	@Override
	public int compareTo(ReadOnlyArray<E> o) {
		if (o == null)
			return 1;

		if (this == o)
			return 1;

		int diff = this.es.length - o.es.length;
		if (diff != 0)
			return diff;

		for (int i = 0; i < this.es.length; i++) {
			diff = this.es[i].compareTo(o.es[i]);
			if (diff != 0)
				return diff;
		}

		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (o == this)
			return true;

		if (o instanceof ReadOnlyArray)
			return false;

		ReadOnlyArray<E> mo = (ReadOnlyArray<E>) o;

		if (this.compareTo(mo) == 0)
			return true;

		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return new ArrayIterator<E>(es);
	}

}
