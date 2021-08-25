package net.board.db;

import java.sql.Date;

public class BoardBean {
private String BOARD_ID;
private int BOARD_NUM;
private String BOARD_TITLE;
private String BOARD_CONTENT;
private int BOARD_LIKE;
private String BOARD_COMMENT;
private Date BOARD_DATE;
private String BOARD_IMAGE;


public String getBOARD_IMAGE() {
	return BOARD_IMAGE;
}
public void setBOARD_IMAGE(String bOARD_IMAGE) {
	BOARD_IMAGE = bOARD_IMAGE;
}
public String getBOARD_ID() {
	return BOARD_ID;
}
public void setBOARD_ID(String bOARD_ID) {
	BOARD_ID = bOARD_ID;
}
public int getBOARD_NUM() {
	return BOARD_NUM;
}
public void setBOARD_NUM(int bOARD_NUM) {
	BOARD_NUM = bOARD_NUM;
}
public String getBOARD_TITLE() {
	return BOARD_TITLE;
}
public void setBOARD_TITLE(String bOARD_TITLE) {
	BOARD_TITLE = bOARD_TITLE;
}
public String getBOARD_CONTENT() {
	return BOARD_CONTENT;
}
public void setBOARD_CONTENT(String bOARD_CONTENT) {
	BOARD_CONTENT = bOARD_CONTENT;
}
public int getBOARD_LIKE() {
	return BOARD_LIKE;
}
public void setBOARD_LIKE(int bOARD_LIKE) {
	BOARD_LIKE = bOARD_LIKE;
}
public String getBOARD_COMMENT() {
	return BOARD_COMMENT;
}
public void setBOARD_COMMENT(String bOARD_COMMENT) {
	BOARD_COMMENT = bOARD_COMMENT;
}
public Date getBOARD_DATE() {
	return BOARD_DATE;
}
public void setBOARD_DATE(Date bOARD_DATE) {
	BOARD_DATE = bOARD_DATE;
}


}
