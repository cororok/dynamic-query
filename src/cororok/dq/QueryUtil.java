package cororok.dq;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import cororok.dq.mapper.ParameterMapper;
import cororok.dq.mapper.ResultSetMapper;
import cororok.dq.parser.Characters;
import cororok.dq.util.JavaTypes;
import cororok.dq.util.MetaInfoMap;
import cororok.dq.util.ReadOnlyArray;
import cororok.dq.util.ReadOnlyInts;
import cororok.dq.util.ReflectInfo;

/**
 * It should not be called by multiple threads at the same time. Each thread
 * should call its own this instance. Basically it calls
 * {@link java.sql.PreparedStatement} and {@link java.sql.ResultSet}. It does 1.
 * set parameters 2. executes 3. return result.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class QueryUtil extends ResultSetQueryUtil {

	ParameterMapper parameterMapper;
	ResultSetMapper resultSetMapper;

	public ReadOnlyArray<String> getColumns() {
		return columns;
	}

	public ReadOnlyInts getTypes() {
		return types;
	}

	ReadOnlyArray<String> columns;
	ReadOnlyInts types;

	ReflectInfo reflectInfo;
	final int sizeOfParameters;

	/**
	 * It needs parameterMapper and resultSetMapper to map java and database.
	 * 
	 * @param query
	 * @param parameterMapper
	 * @param resultSetMapper
	 */
	public QueryUtil(Query query, ParameterMapper parameterMapper,
			ResultSetMapper resultSetMapper) {
		super(query);
		this.sizeOfParameters = query.sizeOfParameters();
		this.parameterMapper = parameterMapper;
		this.resultSetMapper = resultSetMapper;
	}

	/**
	 * {@link java.sql.PreparedStatement#addBatch}
	 */
	public void addBatch() throws SQLException {
		this.ps.addBatch();
		this.reset();
	}

	/**
	 * {@link java.sql.PreparedStatement#clearBatch}
	 */
	public void clearBatch() throws SQLException {
		this.ps.clearBatch();
	}

	protected String convertColumnName(String columnNameInSQL) {
		return Characters.convertColumnName(columnNameInSQL);
	}

	/**
	 * {@link java.sql.PreparedStatement#execute}
	 */
	public boolean execute() throws SQLException {
		reset();

		return this.ps.execute();
	}

	/**
	 * It does 1. setMap(values); 2 execute();
	 * 
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public boolean execute(Map<String, ?> values) throws SQLException {
		setMap(values);
		return execute();
	}

	/**
	 * It does 1. setBean(parameterBean); 2. execute();
	 * 
	 * @param parameterBean
	 * @return
	 * @throws Exception
	 */
	public boolean execute(Object parameterBean) throws Exception {
		setBean(parameterBean);
		return execute();
	}

	/**
	 * {@link java.sql.PreparedStatement#executeBatch}
	 */
	public int[] executeBatch() throws SQLException {
		return this.ps.executeBatch();
	}

	/**
	 * It does 1. setParameters(objs); 2. execute();
	 * 
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public boolean executeParameters(Object... objs) throws Exception {
		setParameters(objs);
		return execute();
	}

	/**
	 * {@link java.sql.PreparedStatement#executeQuery}
	 */
	public ResultSet executeQuery() throws SQLException {
		reset();

		this.rs = this.ps.executeQuery();
		updateMetaData();

		return rs;
	}

	/**
	 * It does 1. setMap(values); 2 executeQuery();
	 * 
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(Map<String, ?> values) throws SQLException {
		setMap(values);
		return executeQuery();
	}

	/**
	 * It does 1. setMap(parameterBean); 2 executeQuery();
	 * 
	 * @param parameterBean
	 * @return
	 * @throws Exception
	 */
	public ResultSet executeQuery(Object parameterBean) throws Exception {
		setBean(parameterBean);
		return executeQuery();
	}

	/**
	 * It does 1. setParameters(objs); 2. executeQuery();
	 * 
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public ResultSet executeQueryParameters(Object... objs) throws Exception {
		setParameters(objs);
		return executeQuery();
	}

	/**
	 * It does 1. setParameters(objs); 2. executeQueryPopulateArray();
	 * 
	 * the name of this method is too long but want to not be confused with
	 * executeQueryPopulateArray(Object parameterBean) when objs is a single.
	 * 
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Object[] executeQueryPopulateArrayWithParameters(Object... objs)
			throws Exception {
		setParameters(objs);
		return executeQueryPopulateArray();
	}

	/**
	 * it does 1. executeQuery(); 2. rs.next() 3. populateArray(); if No 2 is ok
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Object[] executeQueryPopulateArray() throws SQLException {
		executeQuery();

		if (rs.next() == false)
			return null;

		return populateArray();
	}

	/**
	 * it does 1. setMap(values); 2. executeQueryPopulateArray();
	 * 
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public Object[] executeQueryPopulateArray(Map<String, ?> values)
			throws SQLException {
		setMap(values);
		return executeQueryPopulateArray();
	}

	/**
	 * it does 1. setBean(parameterBean); 2. executeQueryPopulateArray();
	 * 
	 * @param parameterBean
	 * @return
	 * @throws Exception
	 */
	public Object[] executeQueryPopulateArray(Object parameterBean)
			throws Exception {
		setBean(parameterBean);
		return executeQueryPopulateArray();
	}

	/**
	 * it does 1. executeQuery(); 2. rs.next() 3. updateBean(bean); if No 2 is
	 * ok
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public boolean executeQueryUpdateBean(Object bean) throws Exception {
		executeQuery();

		if (rs.next() == false)
			return false;

		updateBean(bean);
		return true;
	}

	/**
	 * it doses 1. setMap(values); 2. executeQueryUpdateBean(bean);
	 * 
	 * @param bean
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public boolean executeQueryUpdateBean(Object bean, Map<String, ?> values)
			throws Exception {
		setMap(values);
		return executeQueryUpdateBean(bean);
	}

	/**
	 * It does 1. setBean(parameterBean); 2. executeQueryUpdateBean(bean);
	 * 
	 * @param bean
	 * @param parameterBean
	 * @return
	 * @throws Exception
	 */
	public boolean executeQueryUpdateBean(Object bean, Object parameterBean)
			throws Exception {
		setBean(parameterBean);
		return executeQueryUpdateBean(bean);
	}

	/**
	 * It does 1. setParameters(objs); 2. executeQueryUpdateBean(bean);
	 * 
	 * the name of this method is too long but want to not be confused with
	 * executeQueryUpdateBean(Object bean, Object parameterBean) when objs is a
	 * single.
	 * 
	 * @param bean
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public boolean executeQueryUpdateBeanWithParameters(Object bean,
			Object... objs) throws Exception {
		setParameters(objs);
		return executeQueryUpdateBean(bean);
	}

	/**
	 * {@link java.sql.PreparedStatement#executeUpdate}
	 */
	public int executeUpdate() throws SQLException {
		reset();

		return this.ps.executeUpdate();
	}

	/**
	 * It does 1. setMap(values); 2. executeUpdate();
	 * 
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(Map<String, ?> values) throws SQLException {
		setMap(values);
		return executeUpdate();
	}

	/**
	 * It does 1. setBean(values); 2. executeUpdate();
	 * 
	 * @param parameterBean
	 * @return
	 * @throws Exception
	 */
	public int executeUpdate(Object parameterBean) throws Exception {
		setBean(parameterBean);
		return executeUpdate();
	}

	/**
	 * It does 1. setParameters(objs); 2. executeUpdate();
	 * 
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public int executeUpdateParameters(Object... objs) throws Exception {
		setParameters(objs);
		return executeUpdate();
	}

	/**
	 * You should call rs.next() first to see if there is available row in rs.
	 * It returns values of rs as an array. It uses resultSetMapper given by
	 * constructor.
	 * 
	 * @return values of rs for columns
	 * @throws SQLException
	 */
	public Object[] populateArray() throws SQLException {
		Object[] results = new Object[this.columns.size()];

		updateArray(results);
		return results;
	}

	/**
	 * set value of fields of bean to ps. It uses parameterMapper given by
	 * constructor.
	 * 
	 * @param bean
	 *            It should have fields matching parameters of query. for
	 *            example if the query is "... where user_id =?" then the bean
	 *            should have userId field.
	 * @throws Exception
	 *             if there is no field for parameters of query. And reflection
	 *             error.
	 */
	public void setBean(Object bean) throws Exception {
		ReflectInfo info = MetaInfoMap.getReflectlInfo(bean.getClass());

		int size = query.sizeOfParameters();
		int i = 0;
		int fieldIndex;
		while (i < size) {
			String param = query.getParameter(i);

			fieldIndex = info.getIndexOfField(param);
			if (fieldIndex == -1)
				throw new Exception("In the bean, can't find " + param);

			Object value = info.getFieldValue(fieldIndex, bean);
			parameterMapper.setParameterValue(ps, ++i, value,
					info.getFieldType(fieldIndex));
		}
	}

	/**
	 * set value of map to ps. It uses parameterMapper given by constructor.
	 * 
	 * @param values
	 *            the map should have keys matching parameters of query.
	 * @throws SQLException
	 *             If there is no key in the map for parameters of query.
	 */
	public void setMap(Map<String, ?> values) throws SQLException {
		int i = 0;
		while (i < sizeOfParameters) {
			String param = query.getParameter(i);
			if (values.containsKey(param) == false)
				throw new SQLException("No key in the map for " + param);

			Object value = values.get(param);
			parameterMapper.setParameterValue(ps, ++i, value);
		}
	}

	protected void setMetaData(ReadOnlyArray<String> columns, ReadOnlyInts types)
			throws SQLException {
		this.columns = MetaInfoMap.getOrPutColumns(columns);
		this.types = MetaInfoMap.getOrPutTypes(types);

		synchronized (query) {
			if (query.getColumns() != null) // double check
				query.setMetaData(columns, types);
		}

	}

	/**
	 * # of objs should be the same # of parameters. It sets objs to ps. It uses
	 * parameterMapper given by constructor.
	 * 
	 * @param objs
	 *            values of the parameter
	 * @throws SQLException
	 */
	public void setParameters(Object... objs) throws SQLException {
		if (objs.length != sizeOfParameters)
			throw new SQLException("wrong size, expected " + sizeOfParameters
					+ " but is given " + objs.length);

		int i = 0;
		while (i < sizeOfParameters) {
			Object value = objs[i];
			parameterMapper.setParameterValue(ps, ++i, value);
		}
	}

	/**
	 * use it when sizeOfParameters is 1.
	 * 
	 * @param obj
	 *            value of the parameter
	 * @throws SQLException
	 */
	public void setParameters(Object obj) throws SQLException {
		if (1 != sizeOfParameters)
			throw new SQLException("sizeOfParameters is not 1 but it is "
					+ sizeOfParameters);

		parameterMapper.setParameterValue(ps, 1, obj);
	}

	/**
	 * You should call rs.next() first to see if there is available row in rs.
	 * It updates given array with values of rs so the array can be reused. It
	 * uses resultSetMapper given by You should call rs.next() first to see if
	 * there is available row in rs. constructor.
	 * 
	 * @param results
	 *            an array to be updated by resultSetMapper.getObject(..);
	 * @throws SQLException
	 */
	public void updateArray(Object[] results) throws SQLException {
		int i = 0;

		while (i < this.columns.size()) {
			int columnType = this.types.get(i);
			results[i] = resultSetMapper.getObject(rs, ++i, columnType);
		}
	}

	/**
	 * You should call rs.next() first to see if there is available row in rs.It
	 * updates given bean with values of rs so the bean can be reused. If the
	 * name of field of bean is the same name of column of rs it gets the value
	 * from rs and sets the value to bean. It can be no update at all if there
	 * is no the same name between columns of rs and fields of the bean.
	 * 
	 * @param bean
	 *            a bean to be updated by rs.getXXX();
	 * @throws Exception
	 */
	public void updateBean(Object bean) throws Exception {
		updateBeanInfo(bean);

		String columnName;
		int fieldIndex;
		int fieldType;
		for (int i = 0; i < this.columns.size(); i++) {
			int columnIndex = i + 1;
			columnName = this.columns.get(i);
			fieldIndex = this.reflectInfo.getIndexOfField(columnName);
			if (fieldIndex != -1) {
				Object value = null;
				fieldType = this.reflectInfo.getFieldType(fieldIndex);

				switch (fieldType) {
				case JavaTypes.STRING:
					value = rs.getString(columnIndex);
					break;
				case JavaTypes.BIGDECIMAL:
					value = rs.getBigDecimal(columnIndex);
					break;
				case JavaTypes.INTEGER:
					value = new Integer(rs.getInt(columnIndex));
					break;
				case JavaTypes.DOUBLE:
					value = new Double(rs.getDouble(columnIndex));
					break;

				case JavaTypes.LONG:
					value = new Long(rs.getLong(columnIndex));
					break;

				case JavaTypes.FLOAT:
					value = new Float(rs.getFloat(columnIndex));
					break;

				case JavaTypes.DATE:
					value = new Date(rs.getTimestamp(columnIndex).getTime());
					break;
				case JavaTypes.TIMESTAMP_SQL:
					value = rs.getTimestamp(columnIndex);
					break;

				case JavaTypes.DATE_SQL:
					value = rs.getDate(columnIndex);
					break;
				case JavaTypes.TIME_SQL:
					value = rs.getTime(columnIndex);
					break;
				case JavaTypes.BOOLEAN:
					value = new Boolean(rs.getBoolean(columnIndex));
					break;

				default:
					throw new Exception("unknown type for column " + columnName);
				}
				reflectInfo.setFieldValue(fieldIndex, bean, value);
			}
		}

	}

	protected void updateBeanInfo(Object bean) {
		if (this.reflectInfo != null)
			return;

		this.reflectInfo = MetaInfoMap.getReflectlInfo(bean.getClass());
	}

	protected void updateMetaData() throws SQLException {
		if (this.columns != null)
			return;

		ReadOnlyArray<String> columns = query.getColumns();
		if (columns != null) {
			this.columns = columns;
			this.types = query.getTypes();
			return;
		}

		// build a new one
		ResultSetMetaData meta = rs.getMetaData();
		int columnCount = meta.getColumnCount();

		String[] columnNames = new String[columnCount];
		int[] columnTypes = new int[columnCount];

		for (int i = 0; i < columnCount; i++) {
			int index = i + 1;
			columnNames[i] = convertColumnName(meta.getColumnName(index));
			columnTypes[i] = meta.getColumnType(index);
		}

		setMetaData(new ReadOnlyArray<String>(columnNames), new ReadOnlyInts(
				columnTypes));
	}

	/**
	 * convenient static method to ignore exception
	 * 
	 * @param connection
	 */
	public static void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
		}
	}

	/**
	 * convenient static method to ignore exception
	 * 
	 * @param conn
	 */
	public static void commit(Connection connection) {
		try {
			connection.commit();
		} catch (SQLException e) {
		}
	}

	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
		}
	}

}
