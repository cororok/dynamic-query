package examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import cororok.dq.Query;
import cororok.dq.QueryFactory;
import cororok.dq.QueryMap;
import cororok.dq.QueryUtil;
import cororok.dq.parser.ParsingException;
import cororok.dq.util.QueryMapHelper;

public class Sample {

	String jdbcClass = "com.mysql.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test";

	String jdbcUserId = "testuser";
	String jdbcUserPw = "test1234";

	QueryMap qm;
	QueryMap qm2; // you can create a multiple qm.

	String queryFilePath = "examples/sample.sql";
	{
		try {
			Class.forName(jdbcClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			return DriverManager.getConnection(jdbcUrl, jdbcUserId, jdbcUserPw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) {
		Sample ex = new Sample();
		Connection conn = null;
		try {
			ex.init();

			// sql tests
			ex.testSQL();
			ex.testSubSQL();

			// db tests
			conn = ex.getConnection();

			ex.deleteAll(conn);
			ex.select(conn);
			ex.insertIfEmpty(conn);
			ex.select(conn);

			ex.basicSelect(conn);

			ex.select(conn);
			ex.transaction(ex.getConnection(), "alpha", "me", 1);
			ex.transaction(ex.getConnection(), "alpha", "beta", 1000);
			ex.transaction(ex.getConnection(), "alpha", "beta", 2);

		} catch (ParsingException e) {
			e.printStackTrace();
		}
		QueryUtil.closeConnection(conn);
	}

	private int insertIfEmpty(Connection connection) {
		if (basicSelect(connection) > 0)
			return 0;

		// $user_name, $email_address, $amount,
		QueryUtil qu = qm.createQueryUtil("insertUser");
		int result = 0;
		try {
			qu.setConnection(connection, false);
			// with native jdbc
			qu.setString("alpha");
			qu.setString("alpha@email.com");
			qu.setDouble(10.1);
			qu.setTimestamp(new Timestamp(System.currentTimeMillis()));
			result += qu.executeUpdate();

			// with bean
			User user = new User();
			user.setUserName("beta");
			user.setEmailAddress("beta@email.com");
			user.setAmount(20.2);
			user.setCreated(new Timestamp(System.currentTimeMillis()));
			result += qu.executeUpdate(user);

			// with blankBean
			User blankBean = new User();
			result += qu.executeUpdate(blankBean);

			// with array -- be careful of types
			result += qu.executeUpdateParameters("gamma", "gamma@gmail.com",
					30.3, new Timestamp(System.currentTimeMillis()));

			// with empty array
			result += qu.executeUpdateParameters(null, null, null, null);

			// with map
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName", "delta"); // not user_name
			map.put("emailAddress", "delta@email.com"); // not email_address
			map.put("amount", 40.4);
			map.put("created", new Timestamp(System.currentTimeMillis()));
			result += qu.executeUpdate(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result == 6)
				QueryUtil.commit(connection);
			else
				QueryUtil.rollback(connection);

			qu.closeJust();
		}

		if (result != 6)
			throw new RuntimeException("should be 6 but " + result);

		System.out.println("############# insertIfEmpty ##########");
		System.out.println("inserted " + result);
		return result;
	}

	private int basicSelect(Connection conn) {
		System.out.println("############# basicSelect ##########");
		QueryUtil qu = qm.createQueryUtil("basicSelect");
		int result = 0;
		try {
			qu.setConnection(conn);
			qu.executeQuery();
			if (qu.next()) // == qu.rs.next()
				result = qu.getInt(1); // == qu.rs.getInt(1)

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qu.closeJust();
		}

		System.out.println("result=" + result);
		return result;
	}

	private void select(Connection conn) {
		System.out.println("############# select ##########");
		QueryUtil qu = qm.createQueryUtil("selectAll");
		try {
			qu.setConnection(conn);
			qu.executeQuery();

			while (qu.next()) // == qu.rs.next()
			{
				// bean
				User user = new User();
				qu.updateBean(user);
				printUser(user);

				// array
				Object[] values = qu.populateArray();
				printArray(values);

				// or native jdbc
				String usreName = qu.getString("user_name"); // not userName
				String emailAddress = qu.getString("email_address"); // not
																		// emailAddress
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qu.closeJust();
		}
	}

	private int transaction(Connection conn, String from, String to,
			double amount) {
		System.out.println("############# transaction ##########");
		QueryUtil getAmount = qm.createQueryUtil("getAmount");
		QueryUtil updateAmount = qm.createQueryUtil("updateAmount", "normal");
		QueryUtil updateAmountAdd = qm.createQueryUtil("updateAmount", "add");

		int result = 0;
		try {
			getAmount.setConnection(conn, false);
			updateAmount.setConnection(conn); // don't need to set autoCommit
			updateAmountAdd.setConnection(conn);

			getAmount.executeQueryParameters(from);
			double oldAmount;
			if (getAmount.next()) {
				oldAmount = getAmount.getDouble(1);
				if (oldAmount < amount)
					throw new Exception("not enough: " + oldAmount + " < "
							+ amount);
			} else
				throw new Exception("no data for " + from);

			// update from
			double newAmount = oldAmount - amount;
			result = updateAmount.executeUpdateParameters(newAmount, from);
			if (result == 0)
				throw new Exception("update faied for " + from + " , amt="
						+ newAmount);

			// update to
			result = updateAmountAdd.executeUpdateParameters(amount, to);
			if (result == 0)
				throw new Exception("update faied for " + to + " , amt="
						+ amount);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result == 1) {
				QueryUtil.commit(conn);
				System.out.println("finished commit");
			} else {
				System.out.println("rollback...");
				QueryUtil.rollback(conn);
			}
			getAmount.closeJust();
			updateAmount.closeJust();
			updateAmountAdd.closeJust();
		}

		return result;
	}

	private void printArray(Object[] values) {
		System.out.println("-- values of array is ---");
		for (int i = 0; i < values.length; i++) {
			System.out.println("values[" + i + "]=" + values[i]);
		}
	}

	private void printUser(User user) {
		System.out.println("--- bean user is ---");
		System.out.println(user.toString());
	}

	private void testSQL() {
		Query q = qm.getQuery("basicSelect");
		printQuery(q);

		Query user = qm.getQuery("insertUser");
		printQuery(user);
	}

	private void printQuery(Query q) {
		System.out.println("########### printQuery ###############");
		System.out.println("id =" + q.getId());
		System.out.println("sql =" + q.getText());
		System.out.println("parameters : ");
		for (int i = 0; i < q.sizeOfParameters(); i++) {
			System.out.println(i + "=" + q.getParameter(i));
		}
	}

	private void testSubSQL() {
		Query q = qm.getQuery("updateAmount");
		printQuery(q);

		Query subQuery = qm.getQuery(q, "normal");
		printQuery(subQuery);

		Query subQuery2 = qm.getQuery(q, "normal", "checkEmail");
		printQuery(subQuery2);
	}

	public void init() throws ParsingException {
		// use IOC, singleton or static class to keep it.
		// should be called in synchronized block
		qm = QueryFactory.createQueryMap(queryFilePath);

		// look at dq.properties
		qm2 = QueryMapHelper.getMap("pathTest");
	}

	public void deleteAll(Connection conn) {
		System.out.println("########### deleteAll ###############");
		QueryUtil qu = qm.createQueryUtil("deleteAll");
		try {
			qu.setConnection(conn);
			System.out.println("deleted # of rows :" + qu.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			QueryUtil.commit(conn);
			qu.closeJust();
		}
	}

}
