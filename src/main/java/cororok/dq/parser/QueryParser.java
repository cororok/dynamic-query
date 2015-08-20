package cororok.dq.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * reads input sources and calls context to handle characters. It can call an external file recursively.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class QueryParser implements CommentHandler, FileInfo {

	URL url;
	CommentFilter filter;
	TokenContext context;

	Logger logger;
	static final int BUFFER_SIZE = 16 * 1024;

	int rowNum = 1;
	int colNum = 1;

	int offsetTotalMainQueries;
	int offsetTotalExtQueries;

	boolean isRoot = false;

	public QueryParser(QueryBuilder queryBuilder) {
		this.context = new TokenContext(queryBuilder, this);
		this.logger = LoggerFactory.getLogger(this.getClass());

		this.filter = new CommentFilter(this);
		this.isRoot = true;
	}

	public QueryParser(QueryParser caller) {
		this.context = caller.context;
		this.logger = caller.logger;

		this.filter = new CommentFilter(this);
		this.context.setFileInfo(this);
	}

	public void parse(Reader reader) throws ParsingException {
		logger.info("starts parsing " + this.url);

		offsetTotalMainQueries = context.sizeOfMainQuery();
		offsetTotalExtQueries = context.sizeOfTotalExtQueries();

		BufferedReader br = null;
		char[] buffer = new char[BUFFER_SIZE];
		int readed;
		try {
			br = new BufferedReader(reader);
			while (true) {
				readed = br.read(buffer, 0, BUFFER_SIZE);
				for (int i = 0; i < readed; i++) {
					filter.process(buffer[i]);
				}
				if (readed < BUFFER_SIZE)
					break;
			}
			context.close();
		} catch (ParsingException e) {
			logger.error(url + ": at row=" + rowNum + ", col=" + colNum);
			throw e;
		} catch (IOException e) {
			throw new ParsingException(e);
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		int totalMainQueries = context.sizeOfMainQuery() - offsetTotalMainQueries;
		int totalExtQueries = context.sizeOfTotalExtQueries() - offsetTotalExtQueries;

		logger.info("ends parsing " + this.url);
		logger.info("total main Queries : " + totalMainQueries + ", total ext Queries : " + totalExtQueries);

		if (isRoot) {
			logger.info("finished every files (If there were nested files)");
			logger.info("Over all, total main Queries : " + context.sizeOfMainQuery() + ", total ext Queries : "
					+ context.sizeOfTotalExtQueries());
		}
	}

	public void parse(URL url) throws ParsingException {
		this.url = url;

		try {
			Reader reader = new InputStreamReader(url.openStream());
			parse(reader);
		} catch (IOException e) {
			throw new ParsingException(e);
		}
	}

	public void parse(String filePath) throws ParsingException {
		File file = new File(filePath);
		if (file.exists()) {
			parse(file);
			return;
		}

		URL url = this.getClass().getClassLoader().getResource(filePath);
		if (url == null)
			throw new ParsingException("can not find the filePath " + filePath);

		parse(url);
	}

	public void parse(File file) throws ParsingException {
		try {
			parse(file.toURI().toURL());
		} catch (MalformedURLException e) {
			throw new ParsingException(e);
		}
	}

	@Override
	public void putChar(char c) throws ParsingException {
		switch (c) {
		case Characters.SPACE:
			context.putSpace();
			break;
		case Characters.SEPARATOR:
			context.putSeparator();
			break;
		case Characters.END:
			context.putEnd();
			break;
		case Characters.PARAMETER:
			context.putParameter();
			break;
		case Characters.SUB_ID:
			context.putSubId();
			break;

		case Characters.TAB:
			context.putTab();
			break;
		case Characters.NEWLINE:
			context.putNewLine();
			++rowNum;
			colNum = 0;
			break;
		default:
			context.putChar(c);
			break;
		}

		postProcess(c);
	}

	private void postProcess(char c) throws ParsingException {
		++colNum;

		String subFile = context.getSubFile();
		if (subFile != null) {
			logger.info("Found a nested sub file. it will process " + subFile);
			context.setSubFile(null);

			int beforeSizeOfMainQueries = context.sizeOfMainQuery();
			int beforeSizeOfExtQueries = context.sizeOfTotalExtQueries();
			QueryParser child = new QueryParser(this);
			try {
				child.parse(new URL(this.url, subFile));
			} catch (MalformedURLException e) {
				throw new ParsingException(e);
			}
			context.setFileInfo(this); // come back

			offsetTotalMainQueries += (context.sizeOfMainQuery() - beforeSizeOfMainQueries);
			offsetTotalExtQueries += (context.sizeOfTotalExtQueries() - beforeSizeOfExtQueries);
		}
	}

	@Override
	public void putCharWhileBlockComment(char c) throws ParsingException {
		checkNewLine(c);
		context.putCharWhileBlockComment(c);
		postProcess(c);
	}

	@Override
	public void putCharWhileLineComment(char c) throws ParsingException {
		checkNewLine(c);
		context.putCharWhileLineComment(c);
		postProcess(c);
	}

	@Override
	public void putCharWhileString(char c) throws ParsingException {
		checkNewLine(c);
		context.putCharWhileString(c);
		postProcess(c);
	}

	private void checkNewLine(char c) {
		if (c == Characters.NEWLINE) {
			++rowNum;
			colNum = 0;
		}
	}

	@Override
	public int getRowNum() {
		return rowNum;
	}

	@Override
	public int getColNum() {
		return colNum;
	}

	@Override
	public String getFilePath() {
		return this.url.getPath();
	}

	@Override
	public void warn(String msg) {
		logger.warn(msg + "\nPlease check it in " + getFilePath() + ": at row=" + rowNum + ", col=" + colNum);
	}
}
