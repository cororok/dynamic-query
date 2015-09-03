package cororok.dq.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReadOnlyIntsTest {

	ReadOnlyInts nullArr = null;

	ReadOnlyInts a12a = new ReadOnlyInts(new int[] { 1, 2 });
	ReadOnlyInts a12b = new ReadOnlyInts(new int[] { 1, 2 });
	ReadOnlyInts a13 = new ReadOnlyInts(new int[] { 1, 3 });
	ReadOnlyInts a123 = new ReadOnlyInts(new int[] { 1, 2, 3 });

	@Test
	public void testEquals() {
		assertFalse(a12a.equals(nullArr));

		assertTrue(a12a.equals(a12b));

		assertFalse(a12a.equals(a13));
		assertFalse(a12a.equals(a123));
		assertFalse(a13.equals(a123));
	}

}
