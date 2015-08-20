package cororok.dq;

import java.util.List;

import cororok.dq.util.ReadOnlyArray;
import cororok.dq.util.ReadOnlyInts;

/**
 * Wrapper of sql and its parameters
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class Query {

	String text;
	String id;

	String[] parameters;

	volatile ReadOnlyArray<String> columns;
	volatile ReadOnlyInts types;

	static final String[] STRING_ARRAY = new String[0];

	Query(String id) {
		this.id = id;
	}

	Query(String id, String text) {
		this.id = id;
		this.text = text;
	}

	Query(String id, String text, String[] parameters) {
		this.id = id;
		this.text = text;
		this.parameters = parameters;
	}

	Query(String id, String text, List<String> parameters) {
		this.id = id;
		this.text = text;

		if (parameters.size() == 0)
			return;
		this.parameters = parameters.toArray(STRING_ARRAY);
	}

	public String getId() {
		return id;
	}

	ReadOnlyArray<String> getColumns() {
		return columns;
	}

	ReadOnlyInts getTypes() {
		return types;
	}

	public String getParameter(int indexOfParameter) {
		return this.parameters[indexOfParameter];
	}

	/**
	 * 
	 * @return returns plain sql containing ?
	 */
	public String getText() {
		return text;
	}

	void setMetaData(ReadOnlyArray<String> columns, ReadOnlyInts types) {
		this.columns = columns;
		this.types = types;
	}

	/**
	 * @return # of ? in the sql.
	 */
	public int sizeOfParameters() {
		return this.parameters == null ? 0 : this.parameters.length;
	}

	@Override
	public String toString() {
		return toStringBuilder().toString();
	}

	StringBuilder toStringBuilder() {
		StringBuilder sb = new StringBuilder();
		sb.append("id :");
		sb.append(id);
		sb.append('\n');
		sb.append("text :");
		sb.append(text);
		sb.append('\n');
		sb.append("paramters : ");
		sb.append(sizeOfParameters());
		sb.append('\n');
		if (parameters != null) {
			for (String param : parameters) {
				sb.append(param);
				sb.append(',');
			}
			sb.append('\n');
		}
		return sb;
	}

}
