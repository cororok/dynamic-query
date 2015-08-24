package cororok.dq;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * It is a wrapper class of {@link java.sql.PreparedStatement}. It will create a PreparedStatement when setConnection
 * method is called and unbound the PreparedStatement when close method is called.
 * 
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public abstract class AbstractQueryUtil {

	int parameterIndex = 1;
	Query query;

	public String getQueryId() {
		return this.query.getId();
	}

	public String getQueryText() {
		return this.query.getText();
	}

	public Query getQuery() {
		return query;
	}

	private Connection connection;

	public PreparedStatement ps;
	public ResultSet rs;

	public AbstractQueryUtil(Query query) {
		this.query = query;
	}

	/**
	 * {@link java.sql.PreparedStatement#clearParameters}
	 */
	public void clearParameters() throws SQLException {
		this.ps.clearParameters();
	}

	/**
	 * {@link java.sql.PreparedStatement#getConnection}
	 */
	public Connection getConnection() throws SQLException {
		return ps.getConnection();
	}

	/**
	 * {@link java.sql.PreparedStatement#getMetaData}
	 */
	public ResultSetMetaData getMetaData() throws SQLException {
		return ps.getMetaData();
	}

	/**
	 * {@link java.sql.PreparedStatement#getParameterMetaData}
	 */
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return ps.getParameterMetaData();
	}

	/**
	 * {@link java.sql.ResultSet#next} calls rs.next();
	 */
	public boolean next() throws SQLException {
		return rs.next();
	}

	/**
	 * {@link java.sql.PreparedStatement#setBigDecimal}
	 */
	public void setBigDecimal(BigDecimal x) throws SQLException {
		this.ps.setBigDecimal(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setBlob}
	 */
	public void setBlob(Blob x) throws SQLException {
		this.ps.setBlob(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setBoolean}
	 */
	public void setBoolean(boolean x) throws SQLException {
		this.ps.setBoolean(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setByte}
	 */
	public void setByte(byte x) throws SQLException {
		this.ps.setByte(parameterIndex++, x);
	}

	/**
	 * creates a PreparedStatement by calling connection.prepareStatement(query.getText())
	 */
	public void setConnection(Connection connection) throws SQLException {
		this.connection = connection;
		this.ps = connection.prepareStatement(query.getText());
	}

	/**
	 * does 1. setConnection(Connection connection); 2. connection.setAutoCommit(autoCommit);
	 * 
	 * @param connection
	 * @param autoCommit
	 * @throws SQLException
	 */
	public void setConnection(Connection connection, boolean autoCommit) throws SQLException {
		setConnection(connection);
		connection.setAutoCommit(autoCommit);
	}

	/**
	 * {@link java.sql.PreparedStatement#setDate}
	 */
	public void setDate(Date x) throws SQLException {
		this.ps.setDate(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setDate}
	 */
	public void setDate(Date x, Calendar cal) throws SQLException {
		this.ps.setDate(parameterIndex++, x, cal);
	}

	/**
	 * {@link java.sql.PreparedStatement#setDouble}
	 */
	public void setDouble(double x) throws SQLException {
		this.ps.setDouble(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setFloat}
	 */
	public void setFloat(float x) throws SQLException {
		this.ps.setFloat(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setInt}
	 */
	public void setInt(int x) throws SQLException {
		this.ps.setInt(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setLong}
	 */
	public void setLong(long x) throws SQLException {
		this.ps.setLong(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setObject}
	 */
	public void setObject(Object x) throws SQLException {
		this.ps.setObject(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setObject}
	 */
	public void setObject(Object x, int targetSqlType) throws SQLException {
		this.ps.setObject(parameterIndex++, x, targetSqlType);
	}

	/**
	 * {@link java.sql.PreparedStatement#setObject}
	 */
	public void setObject(Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		this.ps.setObject(parameterIndex++, x, targetSqlType, scaleOrLength);
	}

	/**
	 * {@link java.sql.PreparedStatement#setShort}
	 */
	public void setShort(short x) throws SQLException {
		this.ps.setShort(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setString}
	 */
	public void setString(String x) throws SQLException {
		this.ps.setString(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setTime}
	 */
	public void setTime(Time x) throws SQLException {
		this.ps.setTime(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setTime}
	 */
	public void setTime(Time x, Calendar cal) throws SQLException {
		this.ps.setTime(parameterIndex++, x, cal);
	}

	/**
	 * {@link java.sql.PreparedStatement#setTimestamp}
	 */
	public void setTimestamp(Timestamp x) throws SQLException {
		this.ps.setTimestamp(parameterIndex++, x);
	}

	/**
	 * {@link java.sql.PreparedStatement#setTimestamp}
	 */
	public void setTimestamp(Timestamp x, Calendar cal) throws SQLException {
		this.ps.setTimestamp(parameterIndex++, x, cal);
	}

	/**
	 * close rs and reset parameterIndex so that it can restart from setting parameters and execute query again.
	 */
	void reset() {
		parameterIndex = 1;
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
			}
	}

	/**
	 * It does not close Connection. it does not commit/rollback that is up to you. It closes PreparedStatement and
	 * ResultSet and unbound them including Connection.
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if (this.rs != null)
			this.rs.close();

		if (this.ps != null)
			this.ps.close();

		clearResouce();
	}

	void clearResouce() {
		this.rs = null;
		this.ps = null;
		this.connection = null;
	}

	/**
	 * Do the same as close() does but it ignores exceptions
	 */
	public void closeJust() {
		if (this.rs != null)
			try {
				this.rs.close();
			} catch (SQLException e1) {
			}

		if (this.ps != null)
			try {
				this.ps.close();
			} catch (SQLException e) {
			}

		clearResouce();
	}

	/**
	 * closeJust() + connection.close();
	 */
	public void closeJustWithConnection() {
		if (this.rs != null)
			try {
				this.rs.close();
			} catch (SQLException e1) {
			}

		if (this.ps != null)
			try {
				this.ps.close();
			} catch (SQLException e) {
			}
		if (this.connection != null)
			try {
				connection.close();
			} catch (Exception e) {
			}

		clearResouce();
	}
}
