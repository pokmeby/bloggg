package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class MemberLogoutAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();

		System.out.println("MEMBER LOG OUT ACTION!!!!");
		
		HttpSession session=request.getSession(false);
        if (session != null) {
            session.removeAttribute("id");
        }
        System.out.println("로그아웃 성공?!");
		forward.setRedirect(true);
		forward.setPath("./BoardList.bo");
		return forward;
        
	}

}
