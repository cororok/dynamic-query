package cororok.dq;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MainQueryTest {

	@Test
	public void testPopulateSubQuery() {
		String sql = "a=?, b=?, c=?";
		MainQuery main = new MainQuery("main", sql);
		main.parameters = new String[] { "va", "vb", "vc" };

		// manually create two extQueries
		String exSql1 = ",x=?, y=?";
		ExtQuery ex1 = new ExtQuery("ex1", exSql1);
		ex1.parameters = new String[] { "vx", "vy" };
		ex1.beginParameter = 1;
		ex1.beginPosition = 3;

		String exSql2 = ",z=?, w=?";
		ExtQuery ex2 = new ExtQuery("ex2", exSql2);
		ex2.parameters = new String[] { "vz", "vw" };
		ex2.beginParameter = 2;
		ex2.beginPosition = 8;
		main.setExtQueries(new ExtQuery[] { ex1, ex2 });

		// now ready to check each subQuery populating
		Query sub1 = new Query("sub1");
		main.populateSubQuery(sub1, ex1.id);
		assertEquals("a=?,x=?, y=?, b=?, c=?", sub1.getText());
		assertArrayEquals(new String[] { "va", "vx", "vy", "vb", "vc" }, sub1.parameters);

		Query sub2 = new Query("sub2");
		main.populateSubQuery(sub2, ex2.id);
		assertEquals("a=?, b=?,z=?, w=?, c=?", sub2.getText());
		assertArrayEquals(new String[] { "va", "vb", "vz", "vw", "vc" }, sub2.parameters);

		Query sub12 = new Query("sub12"); // ex1 + ex2
		main.populateSubQuery(sub12, ex1.id, ex2.id);
		assertEquals("a=?,x=?, y=?, b=?,z=?, w=?, c=?", sub12.getText());
		assertArrayEquals(new String[] { "va", "vx", "vy", "vb", "vz", "vw", "vc" }, sub12.parameters);
	}

}
