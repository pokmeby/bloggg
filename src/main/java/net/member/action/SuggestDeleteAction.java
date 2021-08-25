package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.MemberDAO;

public class SuggestDeleteAction implements Action{

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();
		
		MemberDAO memberdao = new MemberDAO();
		
		boolean result = false;
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		
		System.out.println(num);
		result = memberdao.deleteSuggest(num);
		
		if(result == false) {
			System.out.println("추천 글 삭제 실패");
			return null;
		}
		
		System.out.println("추천 글 삭제 성공");
		
		forward.setRedirect(true);
		forward.setPath("./SuggestList.me");
		return forward;
	}
}
