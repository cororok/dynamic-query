package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public abstract class AbstractCharacterHandler implements CharacterHandler {

	Token token;
	TokenContext context;

	public AbstractCharacterHandler(TokenContext context) {
		this.context = context;
	}

	@Override
	public void putSpace() throws ParsingException {
		throw new UnsupportedCharacterException(this, Characters.SPACE);
	}

	@Override
	public void putSeparator() throws ParsingException {
		throw new UnsupportedCharacterException(this, Characters.SEPARATOR);
	}

	@Override
	public void putEnd() throws ParsingException {
		throw new UnsupportedCharacterException(this, Characters.END);
	}

	@Override
	public void putParameter() throws ParsingException {
		throw new UnsupportedCharacterException(this, Characters.PARAMETER);
	}

	@Override
	public void putSubId() throws ParsingException {
		throw new UnsupportedCharacterException(this, Characters.SUB_ID);
	}

	@Override
	public void putNewLine() throws ParsingException {
		throw new UnsupportedCharacterException(this, Characters.NEWLINE);
	}

	@Override
	public void putCharAsName(char c) throws ParsingException {
		if (token.isClosed())
			throw new NeedValueException();

		Characters.checkValidName(c);
		token.addChar(c);
	}

}
