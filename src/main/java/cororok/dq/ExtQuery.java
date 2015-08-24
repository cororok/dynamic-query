package cororok.dq;

import java.util.List;

/**
 * It is an extended sql from MainQuery.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ExtQuery extends Query {

	/**
	 * The position where this ext Query starts at its main Query. It is used to find the position where this query is
	 * inserted in its main query.
	 */
	int beginPosition;

	/**
	 * # of parameters of its main query until this query starts.
	 */
	int beginParameter;

	ExtQuery(String id, String text) {
		super(id, text);
	}

	public ExtQuery(String id, String text, List<String> parameters) {
		super(id, text, parameters);
	}

	void setWhereItStarts(int beginPosition, int beginParameter) {
		this.beginPosition = beginPosition;
		this.beginParameter = beginParameter;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(", beginPosition :");
		sb.append(beginPosition);
		sb.append(" , beginParameter :");
		sb.append(beginParameter);
		sb.append('\n');
		sb.append(toStringBuilder().toString());

		return sb.toString();
	}
}
