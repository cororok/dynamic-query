package cororok.dq;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cororok.dq.parser.ParsingException;

public class QueryFactoryTest {

	@Test
	public void testCreateQueryMap() throws ParsingException {
		QueryMap qm = QueryFactory.createQueryMap("path.sql");

		assertEquals(3, qm.sizeOfMainQueries());
		assertEquals(0, qm.sizeOfSubQueries());

		Query list0 = qm.getQuery("list0");
		assertEquals("select 1 from x \n", list0.getText());

		Query list00 = qm.getQuery("list00");
		assertEquals("select 0 from x", list00.getText());

		Query list = qm.getQuery("list");
		String expected = "select 11111		\n" + // doesn't trim the end
				"\n" + "/*\n" + " Above query missed ';' at the end.\n"
				+ " so the parser thinks there is no list2 below\n"
				+ " but only one id 'list' containing below llist2.\n" + " see warning.\n" + " */\n" + "\n"
				+ "list2:select 2222";
		assertEquals(expected, list.getText());
	}

	@Test
	public void testParameter() throws ParsingException {
		QueryMap qm = QueryFactory.createQueryMap("test.sql");

		Query parameter = qm.getQuery("parameter");
		assertArrayEquals(new String[] { "userName", "emailAddress" }, parameter.parameters);
		assertEquals("insert into test_user(user_name, email_address)\nvalues( ?, ?)", parameter.getText());

		Query autoParameter = qm.getQuery("autoParameter");
		assertArrayEquals(new String[] { "amt", "qty" }, autoParameter.parameters);
		assertEquals("where amt = ?, qty = 2 * ?", autoParameter.getText());
	}

	@Test
	public void testExtquery() throws ParsingException {
		QueryMap qm = QueryFactory.createQueryMap("test.sql");

		MainQuery main = (MainQuery) qm.getQuery("main");
		assertArrayEquals(new String[] { "name", "id" }, main.parameters);
		assertEquals("select id, ? , email\nfrom tb where id=?  and zip is not null", main.getText());

		assertEquals(2, main.sizeOfExtQueries());
		ExtQuery[] extQs = main.getExtQueries(); // should keep order

		ExtQuery ex1 = extQs[0];
		assertEquals("sub1", ex1.id);
		assertEquals(", zip, ? ", ex1.getText());
		assertArrayEquals(new String[] { "city" }, ex1.parameters);
		assertEquals(1, ex1.beginParameter);
		assertEquals(13, ex1.beginPosition);

		ExtQuery ex2 = extQs[1];
		assertEquals("sub2", ex2.id);
		assertEquals("and email =? ", ex2.getText());
		assertArrayEquals(new String[] { "email" }, ex2.parameters);
		assertEquals(2, ex2.beginParameter);
		assertEquals(40, ex2.beginPosition);
	}

	@Test
	public void testSubquery() throws ParsingException {
		QueryMap qm = QueryFactory.createQueryMap("test.sql");

		Query main = qm.getQuery("main");
		assertArrayEquals(new String[] { "name", "id" }, main.parameters);
		assertEquals("select id, ? , email\nfrom tb where id=?  and zip is not null", main.text);

		Query sub1 = qm.getQuery(main, "sub1");
		assertArrayEquals(new String[] { "name", "city", "id" }, sub1.parameters);
		assertEquals("select id, ? , zip, ? , email\nfrom tb where id=?  and zip is not null", sub1.text);

		Query sub2 = qm.getQuery(main, "sub2");
		assertArrayEquals(new String[] { "name", "id", "email" }, sub2.parameters);
		assertEquals("select id, ? , email\nfrom tb where id=? and email =?  and zip is not null", sub2.text);

		Query sub12 = qm.getQuery(main, "sub2", "sub1");
		assertArrayEquals(new String[] { "name", "city", "id", "email" }, sub12.parameters);
		assertEquals("select id, ? , zip, ? , email\nfrom tb where id=? and email =?  and zip is not null", sub12.text);
	}
}
