package cororok.dq.parser;

/**
 * static class
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class Characters {

	public static final char SPACE = ' ';
	public static final char SEPARATOR = ':';
	public static final char END = ';';
	public static final char PARAMETER = '$';
	public static final char SUB_ID = '@';
	public static final char DOUBLE_QUOTE = '"';
	public static final char SINGLE_QUOTE = '\'';
	public static final char NEWLINE_RETURN = '\r';
	public static final char NEWLINE = '\n';
	public static final char QUESTION = '?';
	public static final char SUB_START = '{';
	public static final char SUB_END = '}';
	public static final char ADD_FILE = '&';
	public static final char DOT = '.';
	public static final char DASH = '-';
	public static final char STAR = '*';
	public static final char SLASH = '/';
	public static final char BACKSLASH = '\\';
	public static final char TAB = '\t';

	/**
	 * @param c
	 * @return true if a-z | A-Z | 0-9 | _
	 */
	public static boolean isValidCharOfName(char c) {
		if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' & c <= '9'
				|| c == '_')
			return true;

		return false;
	}

	public static boolean isWhiteSpace(char c) {
		if (c == Characters.SPACE || c == Characters.NEWLINE
				|| c == Characters.TAB || c == Characters.NEWLINE_RETURN)
			return true;

		return false;
	}

	public static void checkValidName(char c)
			throws UnsupportedCharacterException {
		if (isValidCharOfName(c))
			return;

		throw new UnsupportedCharacterException(c);
	}

	/**
	 * table column name to name of java field. ex) user_name -> userName.
	 * 
	 * A_a/A_A/a_a -> aA
	 * 
	 * a1 -> a1, a_1 -> a_1 : It keeps '_' because to support reverse
	 * conversion.
	 * 
	 * a__a -> a_A
	 * 
	 * @param columnNameInSQL
	 *            table column name
	 * @return name of java fields
	 */
	public static String convertColumnName(String columnNameInSQL) {
		char[] chs = columnNameInSQL.toCharArray();
		char[] result = new char[chs.length];

		char pre = 0;
		int j = 0;

		for (int i = 0; i < chs.length; i++) {
			char ch = chs[i];

			if (i == 0) {
				if (ch >= 'A' && ch <= 'Z')
					ch = (char) (ch + 32); // down

				pre = ch;
				continue;
			}

			if (pre == '_') {
				if ((ch >= 'a' && ch <= 'z')) {
					ch = (char) (ch - 32); // up
				} else if (ch >= 'A' && ch <= 'Z') {
					;
				} else
					result[j++] = pre;
			} else if (ch >= 'A' && ch <= 'Z') {
				ch = (char) (ch + 32); // down
				result[j++] = pre;
			} else {
				result[j++] = pre;
			}
			pre = ch;
		}
		result[j++] = pre; // last one
		return String.valueOf(result, 0, j);
	}

	/**
	 * javaName to sqlName, ex) userName -> user_name
	 * 
	 * @param javaName
	 *            name of java variable
	 * @return name of table column
	 */
	public static String convertJavaName(String javaName) {
		char[] chs = javaName.toCharArray();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chs.length; i++) {
			char ch = chs[i];
			if (ch >= 'A' && ch <= 'Z') {
				sb.append('_');
				sb.append((char) (ch + 32));
			} else
				sb.append(ch);
		}

		return sb.toString();
	}

}
