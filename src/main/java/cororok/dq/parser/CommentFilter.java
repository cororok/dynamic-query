package cororok.dq.parser;

/**
 * detects comments and strings and let a handler notice it.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class CommentFilter {

	boolean isLineComment = false;
	boolean isBlockComment = false;
	boolean isString = false;

	char saved;
	char previous;

	CommentHandler handler;

	public CommentFilter(CommentHandler handler) {
		this.handler = handler;
	}

	public void process(char c) throws ParsingException {
		if (c == Characters.NEWLINE_RETURN)
			return;// ignore, \n will catch it

		if (isString) {
			handler.putCharWhileString(c);
			if (c == Characters.SINGLE_QUOTE
					&& previous != Characters.BACKSLASH)
				isString = false;
		} else if (isLineComment) {
			if (c == Characters.NEWLINE) {
				isLineComment = false;
				handler.putChar(c);
			} else {
				handler.putCharWhileLineComment(c);
			}
		} else if (isBlockComment) {
			handler.putCharWhileBlockComment(c);
			if (previous == '*' && c == '/')
				isBlockComment = false;
		} else if (c == Characters.DASH) {
			if (previous == Characters.DASH) {
				isLineComment = true;
				handler.putCharWhileLineComment(previous);
				clearSaved();

				handler.putCharWhileLineComment(c);
			} else {
				save(c);
			}
		} else if (c == Characters.STAR) {
			if (previous == Characters.SLASH) {
				isBlockComment = true;
				handler.putCharWhileBlockComment(saved);
				clearSaved();

				handler.putCharWhileBlockComment(c);
			} else {
				handler.putChar(c);
			}
		} else if (c == Characters.SLASH) {
			save(c);
		} else {
			if (saved != 0) {
				handler.putChar(saved);
				clearSaved();
			}

			if (c == Characters.SINGLE_QUOTE) {
				this.isString = true;
				handler.putCharWhileString(c);
			} else
				handler.putChar(c);
		}
		previous = c;
	}

	private void save(char c) throws ParsingException {
		if (saved != 0)
			handler.putChar(saved);

		saved = c;
	}

	private void clearSaved() {
		saved = 0;
	}
}
