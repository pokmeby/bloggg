package net.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;
import net.board.db.CommentBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		BoardDAO boarddao=new BoardDAO();
		BoardBean boarddata = new BoardBean();
		
		int num=Integer.parseInt(request.getParameter("num"));
		//조회수 증가
//		boarddao.setReadCountUpdate(num);
		//내용 출력
		boarddata=boarddao.getDetail(num);
		if(boarddata==null) {
			System.out.println("상세보기 실패");
			return null;
		}
		System.out.println("상세보기 성공");
		
		List commentlist = new ArrayList();
		
		int page = 1;
		int limit = 10;
		if(request.getParameter("page")!= null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int listcount = boarddao.getCommentListCount();
		commentlist = boarddao.getCommentList(num, page, limit);
				
		int maxpage=(int)((double)listcount/limit+0.95);
		int startpage = (((int)((double)page/10+0.9))-1)*10+1;
		
		//현재 페이지에 보여줄 마지막 페이지 수 10,20,30 등
		int endpage=maxpage;
		if(commentlist == null) {
			System.out.println("댓글 게시글 페이지 입장 실패");
		}
		System.out.println("추천 게시글 페이지 입장 성공");
		
		if(endpage>startpage+10-1) endpage=startpage+10-1;
		
		request.setAttribute("page", page);		//현재 페이지 수
		request.setAttribute("maxpage", maxpage);	//최대 페이지 수
		request.setAttribute("startpage", startpage);		// 현재 페이지에 표시할 첫 페이지 수
		request.setAttribute("endpage", endpage);		// 현재 페이지에 표시할 끝 페이지 수
		request.setAttribute("listcount", listcount);		//글 수
		request.setAttribute("commentlist", commentlist);
		
		request.setAttribute("boarddata", boarddata);
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./board/board_view.jsp");
		return forward;
	}
}
