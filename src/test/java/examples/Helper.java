package examples;

import cororok.dq.QueryFactory;
import cororok.dq.QueryMap;
import cororok.dq.parser.ParsingException;

public class Helper {
	private static QueryMap map;

	static {
		try {
			new Helper();
		} catch (ParsingException e) {
			e.printStackTrace();
		}
	}

	private Helper() throws ParsingException {
		map = QueryFactory.createQueryMap("examples/sample.sql");
	}

	public static QueryMap getQueryMap() {
		return map;
	}

}
