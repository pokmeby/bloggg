package net.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward=new ActionForward();
		HttpSession session=request.getSession();

		
		// 세션값 있으면 계속 진행
		BoardDAO boarddao=new BoardDAO();
		List<BoardBean> boardlist = new ArrayList<BoardBean>();
		
		int page=1;
		int limit=10;
		
		if(request.getParameter("page")!=null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		// 검색 기능 추가 - 시작
	      String srchKey = request.getParameter("srchKey");
	      String srchFlds = request.getParameter("srchFlds");
	      String cond = null;
	      
	      if(srchKey == null || srchKey.equals("")) {
	         cond = null;
	      } else if (srchFlds.equals("all")) {
	         String whereFmt = " upper(BOARD_TITLE) like '%%' || upper('%s') || '%%' "
	                 + " or upper(BOARD_CONTENT) like '%%' || upper('%s') || '%%' ";
	         cond = String.format(whereFmt, srchKey, srchKey, srchKey);
	      } else if (srchFlds.equals("sub")) {
	         String whereFmt = " upper(BOARD_TITLE) like '%%' || upper('%s') || '%%' ";
	         cond = String.format(whereFmt, srchKey);
	      }  else if (srchFlds.equals("con")) {
	         String whereFmt = " upper(BOARD_CONTENT) like '%%' || upper('%s') || '%%' ";
	         cond = String.format(whereFmt, srchKey);
	      }
	      // 검색 기능 추가 - 끝
	      
		int listcount=boarddao.getListCount(cond);			//총 리스트 수 받아옴
		boardlist=boarddao.getBoardList(page, limit,cond);		//리스트를 받아옴
		

		// 총 페이지 수 
		int maxpage=(int)((double)listcount/limit+0.95); //0.95를 더해서 올림처리
		
		// 현재 페이지에 보여줄 시작 페이지 수 1,11,21 등
		int startpage = (((int)((double)page/10+0.9))-1)*10+1;
		
		//현재 페이지에 보여줄 마지막 페이지 수 10,20,30 등
		int endpage=maxpage;
		
		if(endpage>startpage+10-1) endpage=startpage+10-1;
		
		request.setAttribute("page", page);		//현재 페이지 수
		request.setAttribute("maxpage", maxpage);	//최대 페이지 수
		request.setAttribute("startpage", startpage);		// 현재 페이지에 표시할 첫 페이지 수
		request.setAttribute("endpage", endpage);		// 현재 페이지에 표시할 끝 페이지 수
		request.setAttribute("listcount", listcount);		//글 수
		request.setAttribute("boardlist", boardlist);
		forward.setRedirect(false);
		forward.setPath("./board/qna_board_list.jsp");
		return forward;
	}
}