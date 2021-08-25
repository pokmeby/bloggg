package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;
import net.member.db.SuggestBean;

public class SuggestListAction implements Action{

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
	MemberDAO memberdao = new MemberDAO();
	SuggestBean suggestdata = new SuggestBean();
	ActionForward forward = new ActionForward();
	request.setCharacterEncoding("UTF-8");
	boolean result = false;
	try {
		suggestdata.setSUGGEST_NICKNAME(request.getParameter("SUGGEST_NICKNAME"));
		suggestdata.setSUGGEST_CONTENT(request.getParameter("SUGGEST_CONTENT"));
	
		result = memberdao.suggestInsert(suggestdata);
		
		System.out.println(request.getParameter("SUGGEST_NICKNAME"));
		if(result == false) {
			System.out.println("댓글 등록 실패");
			return null;
		}
		System.out.println("댓글 등록 성공");
		
		forward.setRedirect(true);
		forward.setPath("./SuggestList.me");
		return forward;
	} catch(Exception e) {
		e.printStackTrace();
	}
	return null;
	}
}
