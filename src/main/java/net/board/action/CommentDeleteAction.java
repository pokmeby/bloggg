package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;

public class CommentDeleteAction implements Action{

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	ActionForward forward = new ActionForward();
	BoardDAO boarddao = new BoardDAO();
	
	boolean result = false;
	int bnum=Integer.parseInt(request.getParameter("bnum"));	
	int num = Integer.parseInt(request.getParameter("num"));
	
	result = boarddao.deleteComment(num);
	
	if(result == false) {
		System.out.println("댓글 삭제 실패");
		return null;
	}
	System.out.println("댓글 삭제 성공");
	
	forward.setRedirect(true);
	forward.setPath("BoardDetailAction.bo?num=" + bnum);
	return forward;
	}
}
