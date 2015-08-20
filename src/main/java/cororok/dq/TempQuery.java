package cororok.dq;

import java.util.ArrayList;
import java.util.List;

/**
 * temporary data structure to generate a MainQuery.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class TempQuery {
	public String id;
	public String text;
	public List<String> parameters = new ArrayList<String>();
	public List<TempQuery> tempExtQueries = new ArrayList<TempQuery>();

	public int startPosition;
	public int startParameter;

	public TempQuery(String id) {
		this.id = id;
	}

	public MainQuery createQuery() {
		MainQuery mainQuery = new MainQuery(id, text, this.parameters);

		if (tempExtQueries.size() > 0) {
			ExtQuery[] extQueries = new ExtQuery[tempExtQueries.size()];
			for (int i = 0; i < tempExtQueries.size(); i++) {
				extQueries[i] = tempExtQueries.get(i).createExtQuery();
			}

			mainQuery.setExtQueries(extQueries);
		}
		return mainQuery;
	}

	private ExtQuery createExtQuery() {
		ExtQuery extQuery = new ExtQuery(id, text, parameters);
		extQuery.setWhereItStarts(startPosition, startParameter);
		return extQuery;
	}

}
