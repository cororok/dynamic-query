package cororok.dq.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import examples.BasicUser;
import examples.User;

public class ReflectUtilTest {

	@Test
	public void testBuildReflectInfoBaseClass() {
		String[] names = { "id", "userName" };
		int[] types = { JavaTypes.STRING, JavaTypes.STRING };

		ReflectInfo info = ReflectUtil.buildReflectInfo(BasicUser.class);
		performTest(info, names, types);
	}

	@Test
	public void testBuildReflectInfoSubClass() {
		String[] names = { "amount", "created", "emailAddress", "id", "memo", "userName" };
		int[] types = { JavaTypes.DOUBLE, JavaTypes.TIMESTAMP_SQL, JavaTypes.STRING, JavaTypes.STRING, JavaTypes.STRING,
				JavaTypes.STRING };

		ReflectInfo info = ReflectUtil.buildReflectInfo(User.class);
		performTest(info, names, types);
	}

	void performTest(ReflectInfo info, String[] names, int[] types) {
		assertEquals(names.length, info.size());
		for (int i = 0; i < info.size(); i++) {
			try {
				assertEquals(names[i], info.getFieldName(i));
				assertEquals(types[i], info.getFieldType(i));
			} catch (Throwable e) {
				System.err.println("error at index i=" + i);
				e.printStackTrace();
				throw e;
			}
		}
	}

}
