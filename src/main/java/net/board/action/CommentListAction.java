package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.CommentBean;

public class CommentListAction implements Action{

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BoardDAO boarddao = new BoardDAO();
		CommentBean commentdata = new CommentBean();
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("UTF-8");
		
		int num=Integer.parseInt(request.getParameter("BOARD_NUM"));
		System.out.println("num="+num);
		boolean result = false;
		try {
			commentdata.setCOMMENT1_BNUM(num);
			commentdata.setCOMMENT1_ID(request.getParameter("COMMENT1_ID"));
			commentdata.setCOMMENT1_CONTENT(request.getParameter("COMMENT1_CONTENT"));
			result = boarddao.commentInsert(commentdata);
			
			if(result == false) {
				System.out.println("댓글 등록 실패");
				return null;
			}
			System.out.println("댓글 등록 성공");
			
			forward.setRedirect(true);
			forward.setPath("./BoardDetailAction.bo?num="+num);
			return forward;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		}

	
}
