package cororok.dq.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CharactersTest {

	@Test
	public void testIsValidCharOfName() {
		String valid = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
		char[] validCs = valid.toCharArray();
		for (char validC : validCs) {
			assertTrue(Characters.isValidCharOfName(validC));
		}

		String wrong = "~!@#$%^&*()-+=\"',.<>?|/\\\t\r\n";
		char[] wrongCs = wrong.toCharArray();
		for (char wrongC : wrongCs) {
			assertFalse(Characters.isValidCharOfName(wrongC));
		}
	}

	@Test
	public void testConvertColumnName() {
		assertEquals("aA", Characters.convertColumnName("A_a"));
		assertEquals("aA", Characters.convertColumnName("A_A"));
		assertEquals("aA", Characters.convertColumnName("a_a"));

		assertEquals("a1", Characters.convertColumnName("a1"));
		assertEquals("a_1", Characters.convertColumnName("a_1"));
		assertEquals("a_A", Characters.convertColumnName("a__a"));
		assertEquals("a__A", Characters.convertColumnName("a___a"));
	}

	@Test
	public void testConvertJavaName() {
		assertEquals("username", Characters.convertJavaName("username"));
		assertEquals("user_name", Characters.convertJavaName("userName"));
		assertEquals("my_email_address", Characters.convertJavaName("myEmailAddress"));
	}

}
