package cororok.dq.util;

/**
 * It is good when there are many read access but few write access. It does not lock for read access so can be multiple
 * read access at a time but write access uses a lock.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 * @param <K>
 * @param <V>
 */
public abstract class SafeReadWrite<K, V> {

	private volatile int count;
	private volatile boolean isWriting = false;

	public V read(K k) {
		int countBefore = count;
		if (isWriting)
			return syncRead(k);

		V result = readLogic(k);

		if (isWriting)
			return syncRead(k);

		int countAfter = count;
		if (countBefore != countAfter)
			return syncRead(k);

		return result;
	}

	public synchronized V syncRead(K k) {
		return readLogic(k);
	}

	abstract V readLogic(K k);

	public synchronized V write(K k) {
		isWriting = true;
		++count; // only increase here

		V v = writeLogic(k);
		isWriting = false;
		return v;
	}

	abstract V writeLogic(K k);

}
