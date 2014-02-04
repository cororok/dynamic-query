package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ExternalFileHandler extends IdHandler {

	public ExternalFileHandler(TokenContext context) {
		super(context);
	}

	@Override
	public void putSpace() throws ParsingException {
		token.addChar(Characters.SPACE);
	}

	@Override
	public void putSeparator() throws ParsingException {
		token.addChar(Characters.SEPARATOR);
	}

	@Override
	public void putChar(char c) throws ParsingException {
		token.addChar(c);
	}

	@Override
	public void putNewLine() throws ParsingException {
		throw new NeedValueException(";");
	}

	@Override
	public void putEnd() throws ParsingException {
		close();
	}

	public void close() throws ParsingException {
		super.close();
		exit();
	}

	@Override
	public void exit() throws ParsingException {
		context.setSubFile(token.getStringAndReset().trim());
		context.setHandler(context.idHandler);
	}
}
