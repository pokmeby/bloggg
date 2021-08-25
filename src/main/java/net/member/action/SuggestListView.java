package net.member.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.MemberDAO;
import net.member.db.SuggestBean;

public class SuggestListView implements Action{

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward=new ActionForward();
		MemberDAO memberdao=new MemberDAO();		
		request.setCharacterEncoding("UTF-8");


		List<SuggestBean> suggestlist = new ArrayList<SuggestBean>();
		
		int page = 1;
		int limit = 10;
		
		if(request.getParameter("page")!= null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int listcount = memberdao.getSuggestListCount();
		suggestlist = memberdao.getSuggestList(page, limit);
		
		int maxpage=(int)((double)listcount/limit+0.95);
		int startpage = (((int)((double)page/10+0.9))-1)*10+1;
		
		//현재 페이지에 보여줄 마지막 페이지 수 10,20,30 등
		int endpage=maxpage;
		
		if(suggestlist == null) {
			System.out.println("추천 게시글 페이지 입장 실패");
		}
		System.out.println("추천 게시글 페이지 입장 성공");
		
		if(endpage>startpage+10-1) endpage=startpage+10-1;
		
		request.setAttribute("page", page);		//현재 페이지 수
		request.setAttribute("maxpage", maxpage);	//최대 페이지 수
		request.setAttribute("startpage", startpage);		// 현재 페이지에 표시할 첫 페이지 수
		request.setAttribute("endpage", endpage);		// 현재 페이지에 표시할 끝 페이지 수
		request.setAttribute("listcount", listcount);		//글 수
		request.setAttribute("suggestlist", suggestlist);
		forward.setRedirect(false);
		forward.setPath("./suggest/suggest_list.jsp");
		return forward;
	}
}
