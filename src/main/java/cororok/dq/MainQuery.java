package cororok.dq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cororok.dq.parser.ParsingException;

/**
 * It can have 0 ... N ExtQueries.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class MainQuery extends Query {

	private ExtQuery[] extQueries;

	public MainQuery(String id, String text) {
		super(id, text);
	}

	public MainQuery(String id, String text, List<String> parameters) {
		super(id, text, parameters);
	}

	@Override
	public String toString() {
		StringBuilder sb = toStringBuilder();
		if (extQueries != null) {
			sb.append("extQueries :\n");
			for (int i = 0; i < extQueries.length; i++) {
				sb.append(i + 1);
				sb.append(" ");
				sb.append(extQueries[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * @return # of ExtQueries
	 */
	public int sizeOfExtQueries() {
		return this.extQueries == null ? 0 : this.extQueries.length;
	}

	ExtQuery[] getExtQueries() {
		return extQueries;
	}

	void setExtQueries(ExtQuery[] extQueries) {
		this.extQueries = extQueries;
	}

	/**
	 * populate a subQuery. subQuery = mainQuery + extQueries.
	 * 
	 * @param newSubQuery
	 * @param subIds
	 */
	void populateSubQuery(Query newSubQuery, String... subIds) {
		if (extQueries == null)
			throw new RuntimeException("there is no extQueries");

		List<String> newParameters = new ArrayList<String>();

		StringBuilder sb = new StringBuilder();
		int position = 0;
		int positionParameter = 0;
		boolean[] matched = new boolean[subIds.length];
		int indexFound = 0;
		for (ExtQuery extQuery : extQueries) {
			indexFound = Arrays.binarySearch(subIds, extQuery.getId());
			if (indexFound >= 0) {
				matched[indexFound] = true;
				// text
				sb.append(text.substring(position, extQuery.beginPosition));
				sb.append(extQuery.text);
				position = extQuery.beginPosition;

				// parameters
				for (int i = positionParameter; i < extQuery.beginParameter; i++) {
					newParameters.add(parameters[i]);
				}
				for (int i = 0; i < extQuery.sizeOfParameters(); i++) {
					newParameters.add(extQuery.getParameter(i));
				}
				positionParameter = extQuery.beginParameter;
			}
		}

		// validate
		StringBuilder notFound = null;
		for (int i = 0; i < matched.length; i++) {
			if (matched[i] == false) {
				if (notFound == null)
					notFound = new StringBuilder();

				notFound.append(subIds[i]);
				notFound.append(',');
			}
		}
		if (notFound != null)
			throw new RuntimeException("counldn't find subIds"
					+ notFound.toString());

		// rest of text
		if (position < text.length()) {
			sb.append(text.substring(position, text.length()));
		}
		// rest of parameters
		if (positionParameter < sizeOfParameters()) {
			for (int i = positionParameter; i < sizeOfParameters(); i++) {
				newParameters.add(parameters[i]);
			}
		}

		newSubQuery.text = sb.toString();
		if (sizeOfParameters() == newParameters.size())
			newSubQuery.parameters = this.parameters; // shares the same one
		else if (newParameters.size() > 0)
			newSubQuery.parameters = newParameters.toArray(STRING_ARRAY);
	}

	void updateSharedQuery(Map<String, MainQuery> mainQueries)
			throws ParsingException {
		if (extQueries == null)
			return;

		for (ExtQuery extQuery : extQueries) {
			if (extQuery.text != null)
				continue;

			MainQuery sharedOne = mainQueries.get(extQuery.getId());
			if (sharedOne == null)
				throw new ParsingException("there is no main query, id="
						+ extQuery.getId());

			extQuery.text = sharedOne.text;
			extQuery.parameters = sharedOne.parameters;
		}
	}

}
