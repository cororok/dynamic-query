package examples;

import cororok.dq.QueryMap;
import cororok.dq.util.QueryMapHelper;

public class HelperTest {
	public static void main(String[] args) {
		singleQueryMapCase();
		queryMapHelperTest();
	}

	private static void singleQueryMapCase() {
		QueryMap map = Helper.getQueryMap();

		QueryMap map1 = Helper.getQueryMap();
		System.out.println("should be the same map: " + (map == map1));
	}

	private static void queryMapHelperTest() {
		// look at dq.properties
		QueryMap sampleMap = QueryMapHelper.getMap("sample");
		System.out.println("size=" + sampleMap.sizeOfMainQueries());

		QueryMap pathMap = QueryMapHelper.getMap("pathTest");
		System.out.println("size=" + pathMap.sizeOfMainQueries());
	}
}
