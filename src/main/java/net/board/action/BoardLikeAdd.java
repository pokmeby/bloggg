package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardLikeAdd implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

			request.setCharacterEncoding("UTF-8");
			BoardDAO boarddao=new BoardDAO();
			BoardBean boarddata = new BoardBean();
			
			int num=Integer.parseInt(request.getParameter("num"));
			//조회수 증가
			boarddao.setLikeUpdate(num);
	boarddata=boarddao.getDetail(num);
	if(boarddata==null) {
		System.out.println("상세보기실패");
		return null;
	}
	System.out.println("상세보기 성공");
	
	request.setAttribute("boarddata", boarddata);
	//boarddata를 request로 전달한다
	ActionForward forward=new ActionForward();
	forward.setRedirect(false);
	forward.setPath("BoardDetailAction.bo?num="+ num);
	return forward;
	}

}
