package cororok.dq.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cororok.dq.QueryFactory;
import cororok.dq.QueryMap;
import cororok.dq.parser.ParsingException;

public class QueryMapHelper {

	private static Map<String, QueryMap> maps = new HashMap<String, QueryMap>();

	static {
		new QueryMapHelper();
	}

	private QueryMapHelper() {
		Properties p = new Properties();

		URL url = null;
		String filePath = "dq.properties";
		File file = new File(filePath);
		if (file.exists()) {
			try {
				url = file.toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return;
			}
		} else {
			url = this.getClass().getClassLoader().getResource(filePath);
		}

		if (url == null) {
			throw new RuntimeException("can not find the file " + filePath);
		}

		try {
			p.load(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		loadFiles(p);
	}

	private void loadFiles(Properties pro) {
		Enumeration<Object> enu = pro.keys();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();

			if (key.contains(".") == false) {
				String filePath = pro.getProperty(key).trim();
				String cachId = key + ".size";

				int cacheSize = 0;
				if (pro.contains(cachId)) {
					cacheSize = Integer.parseInt((String) pro.get(cachId));
				}
				try {
					QueryMap qm = QueryFactory.createQueryMap(filePath,
							cacheSize);

					maps.put(key, qm);
				} catch (ParsingException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}

	public static QueryMap getMap(String id) {
		return maps.get(id);
	}

}
