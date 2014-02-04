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

	public static String convertColumnName(String columnNameInSQL) {
		char[] chs = columnNameInSQL.toCharArray();
		char[] result = new char[chs.length];

		boolean changed = false;
		char pre = 0;
		int j = 0;
		for (int i = 0; i < chs.length; i++, j++) {
			char ch = chs[i];
			if (ch == '_') {
				--j;
				changed = true;
			} else if (j > 0 && pre == '_') {
				if (ch >= 'a' && ch <= 'z') {
					result[j] = (char) (ch - 32); // up
					changed = true;
				} else
					result[j] = ch;
			} else if (ch >= 'A' && ch <= 'Z') {
				result[j] = (char) (ch + 32); // down
				changed = true;
			} else
				result[j] = ch;

			pre = ch;
		}
		if (changed)
			return String.valueOf(result, 0, j);
		else
			return columnNameInSQL;
	}

}
