package net.board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.member.db.SuggestBean;

public class BoardDAO {
Connection con;
PreparedStatement pstmt;
ResultSet rs;
DataSource ds;
public BoardDAO() {
	try {
		Context init = new InitialContext();
		ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
	}catch (Exception ex) {
		System.out.println("DB연결에 실패하였습니다. "+ex);
		return;
	}
}
//
public int getListCount(String cond) {
	int x=0;
	
	String sql = "select count(*) from board";
	if(cond != null) 
		sql = sql + " where " + cond;
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(sql);
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			x=rs.getInt(1);
		}
	}catch (Exception ex) {
		System.out.println("getListCount 실패 : "+ex);
	}finally {
		if(rs!=null)try {rs.close();} catch (SQLException ex) { }
		if(pstmt!=null)try {pstmt.close();} catch (SQLException ex) { }
		if(con!=null)try {con.close();} catch (SQLException ex) { }
	}
	return x;
}


//게시판 레코드를 읽어 옴
public List getBoardList(int page, int limit, String cond) {
	String board_list_sql="select * from "+
			"(select rownum rnum, BOARD_NUM, BOARD_ID, BOARD_TITLE, "+
			"BOARD_CONTENT, BOARD_IMAGE, BOARD_LIKE, BOARD_DATE from "+
			"(select * from board) order by BOARD_LIKE desc, BOARD_NUM) "+
			"where rnum>=? and rnum<=? ";
	
	String board_list_sql_fmt = "select * from "
			+ " (select rownum rnum, BOARD_NUM, BOARD_ID, BOARD_TITLE, "
			+ "	 BOARD_CONTENT, BOARD_IMAGE, BOARD_LIKE, BOARD_DATE from "
			+ "	(select * from board where %s order by BOARD_LIKE desc)) "
			+ "	 where rnum>=? and rnum<=? ";
	
	if(cond != null && !cond.equals("")) {
		board_list_sql = String.format(board_list_sql_fmt, cond);
	}
	List<BoardBean> list = new ArrayList<BoardBean>();
	
	int startrow=(page-1)*10+1; //
	int endrow=startrow+limit-1; //
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(board_list_sql);
		pstmt.setInt(1, startrow);
		pstmt.setInt(2, endrow);
		rs=pstmt.executeQuery();
		
		while(rs.next()) {
			BoardBean board=new BoardBean();
			board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
			board.setBOARD_ID(rs.getString("BOARD_ID"));
			board.setBOARD_TITLE(rs.getString("BOARD_TITLE"));
			board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
			board.setBOARD_IMAGE(rs.getString("BOARD_IMAGE"));
			board.setBOARD_LIKE(rs.getInt("BOARD_LIKE"));
			board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
			list.add(board);
		}
		return list;
	}catch (Exception ex) {
		System.out.println("getBoardList 읽어오기 실패 : "+ex);
	}finally {
		if(rs!=null) try {rs.close();} catch (SQLException ex) {	}
		if(pstmt!=null) try {pstmt.close();} catch (SQLException ex) {	}
		if(con!=null) try {con.close();} catch (SQLException ex) {	}
	}
	return null;
}

//게시판 글쓰기
public boolean boardInsert(BoardBean board) {
	int num=0;
	String sql="";
	
	int result=0;
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement("select max(board_num) from board");
		rs=pstmt.executeQuery();
		
		if(rs.next())
			num=rs.getInt(1)+1;
		else
			num=1;
		
		sql="insert into board (BOARD_NUM, BOARD_ID, BOARD_TITLE, ";
		sql+="BOARD_CONTENT, BOARD_LIKE, BOARD_IMAGE, "+
		"BOARD_DATE) values(?,?,?,?,?,?,sysdate)";
		
		pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, num);
		pstmt.setString(2, board.getBOARD_ID());
		pstmt.setString(3, board.getBOARD_TITLE());
		pstmt.setString(4, board.getBOARD_CONTENT());
		pstmt.setInt(5, 0);
		pstmt.setString(6, board.getBOARD_IMAGE());
		
		result=pstmt.executeUpdate();
		if(result==0)return false;
		
		return true;
	} catch (Exception e) {
		System.out.println("boardInsert 등록 실패 : "+e);
	}finally {
		if(rs!=null)try {rs.close();}catch(SQLException e ) {}
		if(pstmt!=null)try {pstmt.close();}catch(SQLException e ) {}
		if(con!=null)try {con.close();}catch(SQLException e ) {}
	}
	return false;
}

//좋아요
public void setLikeUpdate(int num) throws Exception{
	String sql = "update board set BOARD_LIKE = BOARD_LIKE+1 where BOARD_NUM = "+num;
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(sql);
		pstmt.executeUpdate();
	} catch (Exception e) {
		System.out.println("setLikeCountUpdate 좋아요 수정 실패 : "+e);
	} finally {
		try {
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
		} catch (Exception e) {}
	}
}

//내용보기
public BoardBean getDetail(int num) throws Exception {
	BoardBean board= null;
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement("select * from board where BOARD_NUM=?");
		pstmt.setInt(1, num);
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			board=new BoardBean();
			board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
			board.setBOARD_ID(rs.getString("BOARD_ID"));
			board.setBOARD_TITLE(rs.getString("BOARD_TITLE"));
			board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
			board.setBOARD_IMAGE(rs.getString("BOARD_IMAGE"));
			board.setBOARD_LIKE(rs.getInt("BOARD_LIKE"));
			board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
		}
		return board;
	} catch (Exception e) {
		System.out.println("getDetail 내용보기 실패 : "+e);
	}finally {
		if(rs!=null)try {rs.close();}catch(SQLException e) {}
		if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
		if(con!=null)try {con.close();}catch(SQLException e) {}
	}
	return null;
}
//넘버에 해당하는 글쓴 사람 비교
public boolean isBoardWriter(int num, String id) {
	System.out.println("id="+id);
	String board_sql="select * from board where BOARD_NUM=?";
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(board_sql);
		pstmt.setInt(1, num);
		rs=pstmt.executeQuery();
		rs.next();
		if(id.equals(rs.getString("BOARD_ID")) || id.equals("admin")) {
			return true;
		}
	} catch (Exception e) {
		System.out.println("isBoardWriter 실패 : "+e);
	} finally {
		try {
			if(pstmt!=null)pstmt.close();
			if(con!=null)pstmt.close();
		}catch (Exception e) { }
	}
	return false;
}
//글 수정
public boolean boardModify(BoardBean boarddata)  throws Exception{
	String sql = "update board set BOARD_TITLE=?, ";
	sql+= "BOARD_CONTENT= ? where BOARD_NUM=? ";
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, boarddata.getBOARD_TITLE());
		pstmt.setString(2, boarddata.getBOARD_CONTENT());
		// 전달 할때마다 Bean 을 활용한다  >> DTO 의 모습
		pstmt.setInt(3, boarddata.getBOARD_NUM());
		pstmt.executeUpdate();
		return true;
	} catch (Exception e) {
		System.out.println("boardModify 수정 실패 : "+e);
	}finally {
		if(rs!=null)try {rs.close();} catch(SQLException e) {}
		if(pstmt!=null)try {pstmt.close();} catch(SQLException e) {}
		if(con!=null)try {con.close();} catch(SQLException e) {}
	}
	return false;
}

public boolean boardDelete(int num) {
	String board_delete_sql="delete from board where BOARD_NUM=?";
	
	int result=0;
	
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(board_delete_sql);
		pstmt.setInt(1, num);
		result=pstmt.executeUpdate();
		if(result==0)return false;    // ==0 보다는 !=0 이 오류가 덜 난다
		return true;
	} catch (Exception e) {
		System.out.println("boardDelete 삭제 실패 : "+e);
	}finally {
		try {
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
		} catch (Exception e) {}
	}
	return false;
}
public boolean commentInsert(CommentBean commentdata) {
	int num=0;
	
	String sql = "";
	int result = 0;
	

	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement("select max(comment1_num) from comment1");
		rs=pstmt.executeQuery();
		
		if(rs.next())
			num=rs.getInt(1)+1;
		else
			num=1;

		sql = "insert into comment1(COMMENT1_BNUM, COMMENT1_NUM, COMMENT1_ID, COMMENT1_DATE, COMMENT1_CONTENT)";
		sql += " values(?,?,?,sysdate,?)";
		
		pstmt=con.prepareStatement(sql);
		pstmt.setInt(1,commentdata.getCOMMENT1_BNUM());
		pstmt.setInt(2, num);
		pstmt.setString(3, commentdata.getCOMMENT1_ID());
		pstmt.setString(4, commentdata.getCOMMENT1_CONTENT());
		
		System.out.println("commentInsert 성공");
		result=pstmt.executeUpdate();
		if(result==0)return false;
		
		return true;
	} catch (Exception e) {
		System.out.println("commentInsert 등록 실패 : "+e);
	}finally {
		if(rs!=null)try {rs.close();}catch(SQLException e ) {}
		if(pstmt!=null)try {pstmt.close();}catch(SQLException e ) {}
		if(con!=null)try {con.close();}catch(SQLException e ) {}
	}
	return false;

	
}
public int getCommentListCount() {
	int x=0;
	
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement("select count(*) from comment1");
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			x=rs.getInt(1);
		}
	}catch (Exception ex) {
		System.out.println("getcommentListCount 실패 : "+ex);
	}finally {
		if(rs!=null)try {rs.close();} catch (SQLException ex) { }
		if(pstmt!=null)try {pstmt.close();} catch (SQLException ex) { }
		if(con!=null)try {con.close();} catch (SQLException ex) { }
	}
	return x;
}
public List getCommentList(int bnum, int page, int limit) {
	String comment_list_sql="select * from "+
			"(select rownum rnum, COMMENT1_BNUM, COMMENT1_NUM, COMMENT1_ID, COMMENT1_DATE, "+
			"COMMENT1_CONTENT from (select * from comment1 where COMMENT1_BNUM=?)) "+
			"where rnum>=? and rnum<=? ";
	
	List list = new ArrayList();
	int startrow=(page-1)*10+1; //
	int endrow=startrow+limit-1; //
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(comment_list_sql);
		pstmt.setInt(1, bnum);
		pstmt.setInt(2, startrow);
		pstmt.setInt(3, endrow);
		rs=pstmt.executeQuery();
		
		while(rs.next()) {
			CommentBean comment =new CommentBean();
			comment.setCOMMENT1_NUM(rs.getInt("COMMENT1_NUM"));
			comment.setCOMMENT1_ID(rs.getString("COMMENT1_ID"));
			comment.setCOMMENT1_DATE(rs.getDate("COMMENT1_DATE"));
			comment.setCOMMENT1_CONTENT(rs.getString("COMMENT1_CONTENT"));
			list.add(comment);
		}
		return list;
	}catch (Exception ex) {
		System.out.println("getCommenttList 읽어오기 실패 : "+ex);
	}finally {
		if(rs!=null) try {rs.close();} catch (SQLException ex) {	}
		if(pstmt!=null) try {pstmt.close();} catch (SQLException ex) {	}
		if(con!=null) try {con.close();} catch (SQLException ex) {	}
	}
	return null;
}
public boolean deleteComment(int num) {
	
	String sql1 = "DELETE FROM COMMENT1 WHERE COMMENT1_num = ?";
	int result1 = 0;
	
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(sql1);
		pstmt.setInt(1, num);
		result1 = pstmt.executeUpdate();
		if(result1==0) return false;
			
		return true;
	} catch(Exception ex) {
		System.out.println("DeleteComment 에러 : " + ex);
	} finally {
		try {
			if(pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		} catch(Exception ex) {}
	}
	return false;
	}
}