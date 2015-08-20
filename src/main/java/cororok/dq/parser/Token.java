package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class Token {

	StringBuilder sb = new StringBuilder();

	private boolean started = false;
	private boolean closed = false;

	public boolean isClosed() {
		return closed;
	}

	public boolean isStarted() {
		return started;
	}

	public void addChar(char c) {
		if (started == false)
			started = true;

		sb.append(c);
	}

	public void addString(String str) {
		if (started == false)
			started = true;

		sb.append(str);
	}

	public void reset() {
		started = false;
		closed = false;
		sb = new StringBuilder();
	}

	public String getStringAndReset() {
		if (closed == false)
			throw new RuntimeException("didn't close yet");
		String result = sb.toString();
		reset();
		return result;
	}

	public int length() {
		return sb.length();
	}

	public void close() {
		closed = true;
		started = false;
	}

	public void start() {
		this.started = true;
	}

	public boolean isBuilding() {
		return started && !closed ? true : false;
	}

	public void removeChar() {
		if (isStarted()) {
			sb.deleteCharAt(sb.length() - 1);
			if (sb.length() == 0)
				started = false;
		}
	}

	public char charAt(int index) {
		return sb.charAt(index);
	}

	/**
	 * find the column name before the first '=', '<=', '>=', '!=' or '<>'
	 * 
	 * @return
	 */
	public String getPreviousColumnName() {
		if (started == false)
			return null;

		StringBuilder previousColumnName = new StringBuilder();
		boolean xStart = false;
		for (int i = sb.length() - 1; i >= 0; i--) {
			char c = sb.charAt(i);
			if (xStart) {
				try {
					if (Characters.isWhiteSpace(c)) {
						if (previousColumnName.length() == 0)
							continue;
						else
							break;
					}
					Characters.checkValidName(c);
					previousColumnName.append(c);
				} catch (UnsupportedCharacterException e) {
					break;
				}
			}

			if (xStart == false) {
				if (c == '=') {
					xStart = true;
					char preC = sb.charAt(i - 1);
					if (preC == '>' || preC == '<' || preC == '!')
						--i;

					continue;
				} else if (c == '>') {
					if (sb.charAt(i - 1) == '<') {
						--i;
						xStart = true;
						continue;
					}
				}
			}
		}
		if (previousColumnName.length() == 0)
			return null;
		else {
			previousColumnName.reverse();
			return previousColumnName.toString();
		}
	}

}
