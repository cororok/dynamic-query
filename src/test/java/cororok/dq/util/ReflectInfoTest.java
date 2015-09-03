package cororok.dq.util;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import examples.User;

public class ReflectInfoTest {

	String[] names = { "amount", "created", "emailAddress", "id", "memo", "userName" };
	int[] types = { JavaTypes.DOUBLE, JavaTypes.TIMESTAMP_SQL, JavaTypes.STRING, JavaTypes.STRING, JavaTypes.STRING,
			JavaTypes.STRING };

	User u;

	final double amountOld = 99;
	final String idOld = "idOld";
	final String userNameOld = "userNameOld";
	final Timestamp createdOld = new Timestamp(1000);

	@Test
	public void testGetFieldValue() throws Exception {
		ReflectInfo info = ReflectUtil.buildReflectInfo(User.class);

		assertEquals(amountOld, info.getFieldValue(0, u));
		assertEquals(idOld, info.getFieldValue(3, u));
		assertEquals(userNameOld, info.getFieldValue(5, u));
		assertEquals(createdOld, info.getFieldValue(1, u));
	}

	@Test
	public void testSetFieldValue() throws Exception {
		ReflectInfo info = ReflectUtil.buildReflectInfo(User.class);

		final Double amountNew = amountOld + 100;
		final String idNew = idOld + "new";
		final String userNameNew = userNameOld + "new";
		final Timestamp createdNew = new Timestamp(2000);

		info.setFieldValue(0, u, amountNew);
		assertEquals(amountNew, u.getAmount().doubleValue(), 0);

		info.setFieldValue(3, u, idNew);
		assertEquals(idNew, u.getId());

		info.setFieldValue(5, u, userNameNew);
		assertEquals(userNameNew, u.getUserName());

		info.setFieldValue(1, u, createdNew);
		assertEquals(createdNew, u.getCreated());
	}

	@Test
	public void testGetIndexOfField() throws Exception {
		ReflectInfo info = ReflectUtil.buildReflectInfo(User.class);
		for (int i = 0; i < names.length; i++) {
			try {
				assertEquals(i, info.getIndexOfField(names[i]));
			} catch (Throwable e) {
				System.err.println("error at index i=" + i);
				e.printStackTrace();
				throw e;
			}
		}
	}

	@Before
	public void initUser() {
		u = new User();

		u.setAmount(amountOld);
		u.setId(idOld);
		u.setUserName(userNameOld);
		u.setCreated(createdOld);
	}

}
