package net.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardFrontController extends HttpServlet implements javax.servlet.Servlet {
	private static final long serialVersionUID = 1L;
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String RequestURI = request.getRequestURI();
		String contextPath=request.getContextPath();
		String command=RequestURI.substring(contextPath.length());
		ActionForward forward=null;
		Action action = null;
		
		System.out.println("requestURI = " + RequestURI);
		System.out.println("contextPath = " + contextPath);
		System.out.println("command = " + command);
		
		if(command.equals("/BoardWrite.bo")) {
			forward=new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./board/qna_board_write.jsp");
	
		}else if(command.equals("/BoardModify.bo")){
		action = new BoardModifyView();
		try {
			forward=action.execute(request,response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		}else if(command.equals("/BoardAddAction.bo")) {
		action = new BoardAddAction();
		try {
			forward=action.execute(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		}else if(command.equals("/CommentDeleteAction.bo")) {
			action = new CommentDeleteAction();
			try {
				forward=action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
//		}else if(command.equals("/BoardReplyView.bo")) {
//			action=new BoardReplyView();
//			try {
//				forward=action.execute(request, response);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}else if(command.equals("/BoardModifyAction.bo")) {
		action=new BoardModifyAction();
		try {
			forward=action.execute(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		}else if(command.equals("/BoardDeleteAction.bo")) {
			action=new BoardDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/BoardList.bo")) {
			action = new BoardListAction();
			try {
				forward=action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/BoardDetailAction.bo")){
			action=new BoardDetailAction();
			try{
				forward=action.execute(request,response);
		}catch(Exception e){
			e.printStackTrace();
			}
		}else if(command.equals("/BoardLike.bo")){
			action=new BoardLikeAdd();
			try{
				forward=action.execute(request,response);
			}catch(Exception e){
				e.printStackTrace();
			
		}
		}else if(command.equals("/CommentListAction.bo")) {
			action = new CommentListAction();
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			} 
		}
			if(forward!=null) {
				if(forward.isRedirect()) {
					response.sendRedirect(forward.getPath());
				}else {
					RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
					dispatcher.forward(request, response);
				}
			}
		}
		
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("board doGet()~~~~~~");
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("board doPost()  $$$$$$$");
		doProcess(request, response);
	}

}
