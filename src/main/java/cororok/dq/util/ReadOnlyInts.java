package cororok.dq.util;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ReadOnlyInts implements Comparable<ReadOnlyInts> {

	private int[] arrs;

	public ReadOnlyInts(int[] arrs) {
		this.arrs = arrs;
	}

	public int get(int index) {
		return arrs[index];
	}

	public int size() {
		return arrs.length;
	}

	@Override
	public int compareTo(ReadOnlyInts o) {
		if (o == null)
			return 1;

		if (this == o)
			return 1;

		int diff = this.arrs.length - o.arrs.length;
		if (diff != 0)
			return diff;

		for (int i = 0; i < this.arrs.length; i++) {
			diff = this.arrs[i] - o.arrs[i];
			if (diff != 0)
				return diff;
		}

		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (this == o)
			return true;

		if (o instanceof ReadOnlyInts)
			return false;

		ReadOnlyInts mo = (ReadOnlyInts) o;

		if (this.compareTo(mo) == 0)
			return true;

		return false;
	}

}
