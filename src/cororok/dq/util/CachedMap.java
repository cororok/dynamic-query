package cororok.dq.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class CachedMap<K, V extends LinkedNode<K>> implements Map<K, V> {

	Map<K, V> map;

	V head;
	V tail;
	int cacheSize;

	public CachedMap(Map<K, V> map, int cacheSize) {
		this.map = map;
		this.cacheSize = cacheSize;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public V getWithoutCycle(Object key) {
		return map.get(key);
	}

	@Override
	public V get(Object key) {
		V v = map.get(key);
		if (v == null)
			return null;

		if (v != head) {
			if (v == tail) {
				tail = (V) v.getPrevios();
			}

			removeLink(v);
			putFirst(v);
		}
		return v;
	}

	private void removeLink(V v) {
		V prev = (V) v.getPrevios();
		V next = (V) v.getNext();
		if (prev != null)
			prev.setNext(next);
		if (next != null)
			next.setPrevios(prev);
	}

	public V put(K key, V value) {
		V v = map.put(key, value);
		if (v == null)
			putFirst(value);

		if (size() > cacheSize)
			remove(tail.getKey());
		return v;
	}

	public void printLinks() {
		if (head != null) {
			System.out.println("head=" + head.getKey());
			if (tail != null)
				System.out.println("tail=" + tail.getKey());

			V node = head;
			while (true) {
				Object pre = null;
				if (node.getPrevios() != null)
					pre = node.getPrevios().getKey();

				System.out.println(node.getKey() + " pre=" + pre);
				node = (V) node.getNext();
				if (node == null)
					return;
			}
		}
	}

	private void putFirst(V value) {
		if (head == null) {
			head = value;
			tail = value;

			value.setNext(null);
			value.setPrevios(null);
			return;
		}

		// there is an existing head
		value.setPrevios(null);
		value.setNext(head);
		head.setPrevios(value);
		this.head = value;
	}

	@Override
	public V remove(Object key) {
		V v = map.remove(key);
		if (v != null) {
			if (v == head) {
				head = (V) v.getNext();
			} else if (v == tail) {
				tail = (V) v.getPrevios();
			}
			removeLink(v);
			v.setNext(null);
			v.setPrevios(null);
		}
		return v;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean equals(Object o) {
		return map.equals(o);
	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}

}
