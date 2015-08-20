package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class IdHandler extends AbstractCharacterHandler {

	public IdHandler(TokenContext context) {
		super(context);
		this.token = new Token();
	}

	@Override
	public void close() throws ParsingException {
		if (token.length() == 0) {
			throw new ParsingException("Id is empty");
		} else {
			token.close();
		}
	}

	@Override
	public void exit() throws ParsingException {
		context.queryBuilder.addId(token);
		context.setHandler(context.textHandler);
	}

	@Override
	public void putChar(char c) throws ParsingException {
		if (token.isClosed())
			throw new NeedValueException();

		if (c == Characters.ADD_FILE) {
			if (token.isStarted())
				throw new UnsupportedCharacterException(Characters.ADD_FILE);
			else {
				context.setHandler(context.externalFileHandler);
				return;
			}
		}

		Characters.checkValidName(c);
		token.addChar(c);
	}

	@Override
	public void putCharWhileBlockComment(char c) throws ParsingException {
	}

	@Override
	public void putCharWhileLineComment(char c) throws ParsingException {
	}

	@Override
	public void putCharWhileString(char c) throws ParsingException {
		throw new UnsupportedCharacterException(c);
	}

	@Override
	public void putNewLine() throws ParsingException {
		putSpace();
	}

	@Override
	public void putSeparator() throws ParsingException {
		close();
		exit();
	}

	@Override
	public void putSpace() throws ParsingException {
		if (token.isBuilding()) {
			close();
		}
	}

	@Override
	public void putTab() throws ParsingException {
		putSpace();
	}

}
