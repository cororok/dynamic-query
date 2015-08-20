package cororok.dq;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * contains delegating methods for {@link java.sql.ResultSet}
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ResultSetQueryUtil extends AbstractQueryUtil {

	public ResultSetQueryUtil(Query query) {
		super(query);
	}

	/**
	 * {@link java.sql.ResultSet#getBigDecimal}
	 */
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return rs.getBigDecimal(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#getBigDecimal}
	 */
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		return rs.getBigDecimal(columnLabel);
	}

	/**
	 * {@link java.sql.ResultSet#getDate}
	 */
	public Date getDate(int columnIndex) throws SQLException {
		return rs.getDate(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#getDate}
	 */
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return rs.getDate(columnIndex, cal);
	}

	/**
	 * {@link java.sql.ResultSet#getDate}
	 */
	public Date getDate(String columnLabel) throws SQLException {
		return rs.getDate(columnLabel);
	}

	/**
	 * {@link java.sql.ResultSet#getDouble}
	 */
	public double getDouble(int columnIndex) throws SQLException {
		return rs.getDouble(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#getFloat}
	 */
	public float getFloat(int columnIndex) throws SQLException {
		return rs.getFloat(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#getFloat}
	 */
	public float getFloat(String columnLabel) throws SQLException {
		return rs.getFloat(columnLabel);
	}

	/**
	 * {@link java.sql.ResultSet#getInt}
	 */
	public int getInt(int columnIndex) throws SQLException {
		return rs.getInt(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#getInt}
	 */
	public int getInt(String columnLabel) throws SQLException {
		return rs.getInt(columnLabel);
	}

	/**
	 * {@link java.sql.ResultSet#getLong}
	 */
	public long getLong(int columnIndex) throws SQLException {
		return rs.getLong(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#getLong}
	 */
	public long getLong(String columnLabel) throws SQLException {
		return rs.getLong(columnLabel);
	}

	/**
	 * {@link java.sql.ResultSet#getObject}
	 */
	public Object getObject(int columnIndex) throws SQLException {
		return rs.getObject(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#setTimestamp}
	 */
	public Object getObject(String columnLabel) throws SQLException {
		return rs.getObject(columnLabel);
	}

	/**
	 * {@link java.sql.ResultSet#getString}
	 */
	public String getString(int columnIndex) throws SQLException {
		return rs.getString(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#getString}
	 */
	public String getString(String columnLabel) throws SQLException {
		return rs.getString(columnLabel);
	}

	/**
	 * {@link java.sql.ResultSet#getTime}
	 */
	public Time getTime(int columnIndex) throws SQLException {
		return rs.getTime(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#getTime}
	 */
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return rs.getTime(columnIndex, cal);
	}

	/**
	 * {@link java.sql.ResultSet#getTime}
	 */
	public Time getTime(String columnLabel) throws SQLException {
		return rs.getTime(columnLabel);
	}

	/**
	 * {@link java.sql.ResultSet#getTime}
	 */
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return rs.getTime(columnLabel, cal);
	}

	/**
	 * {@link java.sql.ResultSet#getTimestamp}
	 */
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return rs.getTimestamp(columnIndex);
	}

	/**
	 * {@link java.sql.ResultSet#getTimestamp}
	 */
	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		return rs.getTimestamp(columnIndex, cal);
	}

}
