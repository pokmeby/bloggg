package net.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
Connection con;
PreparedStatement pstmt;
ResultSet rs;
DataSource ds;
public MemberDAO() {
	try {
		Context init= new InitialContext();
		ds= (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
	}catch (Exception ex) {
		System.out.println("DB연결 실패 : "+ex);
		return;
	}
}
 public int isMember(MemberBean member) {
	String sql = "SELECT MEMBER_PW FROM MEMBER WHERE MEMBER_ID=?";
	int result=-1;
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, member.getMEMBER_ID());
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			if(rs.getString("MEMBER_PW").equals(member.getMEMBER_PW())) {
				result=1;   //일치
			}else {
				result=0; // 불일치
			}
		}else {
			result=-1; //아이디 존재하지 않음
		}
	}catch (Exception ex) {
	System.out.println("isMember 에러 : " +ex);
	}finally {
		if(rs!=null)try {rs.close();}catch(SQLException ex) {}
		if(pstmt!=null)try {pstmt.close();}catch(SQLException ex) {}
		if(con!=null)try {con.close();}catch(SQLException ex) {}
	}
	return result;
} 
 
public boolean joinMember(MemberBean member) {
	String sql = "INSERT INTO MEMBER VALUES (?,?,?,?,?)";
	int result =0;
	
	try {
		con = ds.getConnection();
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, member.getMEMBER_ID());
		pstmt.setString(2, member.getMEMBER_PW());
		pstmt.setString(3, member.getMEMBER_NAME());
		pstmt.setString(4, member.getMEMBER_GENDER());
		pstmt.setString(5, member.getMEMBER_EMAIL());
		result=pstmt.executeUpdate();
		
		if(result!=0) {
			return true;
		}
	}catch (Exception ex) {
		System.out.println("joinMember 에러 : "+ex);
	}finally {
		if(rs!=null) try {rs.close();}catch(SQLException ex){}
		if(pstmt!=null) try {pstmt.close();}catch(SQLException ex){}
		if(con!=null) try {con.close();}catch(SQLException ex){}
	}
	return false;
}

public MemberBean getDetailMember(String id) {
	String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID=?";
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs=pstmt.executeQuery();
		rs.next();
		
		MemberBean mb=new MemberBean();
		mb.setMEMBER_ID(rs.getString("MEMBER_ID"));
		mb.setMEMBER_PW(rs.getString("MEMBER_PW"));
		mb.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
		mb.setMEMBER_GENDER(rs.getString("MEMBER_GENDER"));
		mb.setMEMBER_EMAIL(rs.getString("MEMBER_EMAIL"));
		
		return mb;
	} catch (Exception e) {
		System.out.println("getDetailMember 에러 : "+e);
	}finally {
		if(rs!=null)try {rs.close();}catch(SQLException e) {}
		if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
		if(con!=null)try {con.close();}catch(SQLException e) {}
	}
	return null;
}

public boolean suggestInsert(SuggestBean suggestdata) {
	
	int num = 0;
	String sql = "";
	int result = 0;
	
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement("select max(suggest_num) from suggest");
		rs = pstmt.executeQuery();
		
		if(rs.next()) 
			num = rs.getInt(1)+1;
			else
				num = 1;
		System.out.println("num = " + num);
		sql = "insert into suggest(SUGGEST_NUM,SUGGEST_NICKNAME, SUGGEST_CONTENT, ";
		sql+= "SUGGEST_DATE) values(?, ?, ?, sysdate)";
		pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, num);
		pstmt.setString(2, suggestdata.getSUGGEST_NICKNAME());
		pstmt.setString(3, suggestdata.getSUGGEST_CONTENT());
		
		result = pstmt.executeUpdate();
		if(result==0) return false;
		
		return true;
	} catch (Exception e) {
		System.out.println("suggestInsert 등록 실패 : " + e);
	} finally {
		if(pstmt!=null)try {pstmt.close();}catch(SQLException e ) {}
		if(con!=null)try {con.close();}catch(SQLException e ) {}
	}
	return false;
}

public int getSuggestListCount() {
	int x=0;
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement("select count(*) from suggest");
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			x=rs.getInt(1);
		}
	}catch (Exception ex) {
		System.out.println("getSuggestListCount 실패 : "+ex);
	}finally {
		if(rs!=null)try {rs.close();} catch (SQLException ex) { }
		if(pstmt!=null)try {pstmt.close();} catch (SQLException ex) { }
		if(con!=null)try {con.close();} catch (SQLException ex) { }
	}
	return x;
}

public List getSuggestList(int page, int limit) {
	String suggest_list_sql="select * from "+
			"(select rownum rnum, SUGGEST_NUM, SUGGEST_NICKNAME, SUGGEST_CONTENT, "+
			"SUGGEST_DATE from (select * from suggest) order by SUGGEST_NUM desc ) "+
			"where rnum>=? and rnum<=? ";
	
	List list = new ArrayList();
	
	int startrow=(page-1)*10+1; //
	int endrow=startrow+limit-1; //
	try {
		con=ds.getConnection();
		pstmt=con.prepareStatement(suggest_list_sql);
		pstmt.setInt(1, startrow);
		pstmt.setInt(2, endrow);
		rs=pstmt.executeQuery();
		
		while(rs.next()) {
			SuggestBean suggest =new SuggestBean();
			suggest.setSUGGEST_NUM(rs.getInt("SUGGEST_NUM"));
			suggest.setSUGGEST_NICKNAME(rs.getString("SUGGEST_NICKNAME"));
			suggest.setSUGGEST_CONTENT(rs.getString("SUGGEST_CONTENT"));
			suggest.setSUGGEST_DATE(rs.getDate("SUGGEST_DATE"));
			list.add(suggest);
		}
		return list;
	}catch (Exception ex) {
		System.out.println("getSuggestList 읽어오기 실패 : "+ex);
	}finally {
		if(rs!=null) try {rs.close();} catch (SQLException ex) {	}
		if(pstmt!=null) try {pstmt.close();} catch (SQLException ex) {	}
		if(con!=null) try {con.close();} catch (SQLException ex) {	}
	}
	return null;
}
public boolean deleteSuggest(int num) {
	String sql1 = "DELETE FROM SUGGEST WHERE SUGGEST_num = ?";
	int result1 = 0;
	
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(sql1);
		pstmt.setInt(1, num);
		result1 = pstmt.executeUpdate();
		if(result1==0) return false;
			
		return true;
	} catch(Exception ex) {
		System.out.println("DeleteSuggest 에러 : " + ex);
	} finally {
		try {
			if(pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		} catch(Exception ex) {}
	}
		return false;	
	}
		

public boolean isSuggestWriter(int num) {
	
	String suggest_sql = "select * from suggest where suggest_num = ?";
	
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(suggest_sql);
		pstmt.setInt(1,num);
		rs = pstmt.executeQuery();
		rs.next();
		
	} catch(Exception ex) {
		System.out.println("isSuggestWriter 실패 : " + ex);
	} finally {
		try {
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
		} catch(Exception ex) {}
	}
	return false;
}
}
