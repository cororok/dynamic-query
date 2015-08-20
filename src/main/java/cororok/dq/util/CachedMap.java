package cororok.dq.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class CachedMap<K, V extends LinkedNode<K, V>> implements Map<K, V> {

	Map<K, V> map;

	final int cacheSize;

	LinkedList<V> list = new LinkedList<V>();

	public CachedMap(Map<K, V> map, int cacheSize) {
		this.map = map;

		if (cacheSize < 10)
			this.cacheSize = 20;
		else
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
		V oldV = map.get(key);
		if (oldV == null)
			return null;

		list.moveToFirst(oldV);
		return oldV;
	}

	@Override
	public V put(K key, V value) {
		V oldV = map.put(key, value);

		if (oldV != null)
			list.removeNode(oldV);

		if (size() > cacheSize)
			map.remove(list.removeTail().getKey());

		list.addHead(value);
		return oldV;
	}

	@Override
	public V remove(Object key) {
		V oldV = map.remove(key);

		if (oldV != null)
			list.removeNode(oldV);

		return oldV;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);

		Iterator<? extends V> itr = m.values().iterator();
		while (itr.hasNext()) {
			V v = itr.next();
			list.addHead(v);
		}

		while (map.size() > cacheSize)
			list.removeTail();
	}

	@Override
	public void clear() {
		map.clear();
		list.clear();
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
