package cororok.dq.parser;

public interface FileInfo {
	int getRowNum();

	int getColNum();

	String getFilePath();

	void warn(String msg);
}
