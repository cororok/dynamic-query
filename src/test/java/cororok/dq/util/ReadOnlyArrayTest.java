package cororok.dq.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

public class ReadOnlyArrayTest {

	ReadOnlyArray<String> nullArr = null;

	ReadOnlyArray<String> ab1 = new ReadOnlyArray<>(new String[] { "a", "b" });
	ReadOnlyArray<String> ab2 = new ReadOnlyArray<>(new String[] { "a", "b" });
	ReadOnlyArray<String> ac = new ReadOnlyArray<>(new String[] { "a", "c" });
	ReadOnlyArray<String> abc = new ReadOnlyArray<>(new String[] { "a", "b", "c" });

	@Test
	public void testEquals() {
		assertFalse(ab1.equals(nullArr));

		assertTrue(ab1.equals(ab2));

		assertFalse(ab1.equals(ac));
		assertFalse(ab1.equals(abc));
		assertFalse(ac.equals(abc));
	}

	@Test
	public void testIterator() {
		Iterator<String> itr = abc.iterator();

		assertTrue(itr.hasNext());
		assertEquals("a", itr.next());
		assertEquals("b", itr.next());

		assertTrue(itr.hasNext());
		assertTrue(itr.hasNext());
		assertEquals("c", itr.next());

		boolean failed = false;
		try {
			itr.next();
		} catch (Exception e) {
			failed = true;
		}
		assertTrue(failed);
	}
}
