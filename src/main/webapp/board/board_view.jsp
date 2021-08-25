<%@page import="java.util.List"%>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "net.board.db.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "net.board.db.*" %>

<% BoardBean board=(BoardBean) request.getAttribute("boarddata") ; 
   CommentBean Commnet = (CommentBean)request.getAttribute("commentdata");
   String sess = (String)session.getAttribute("id"); %>
 
  <%
	List commentList =(List)request.getAttribute("commentlist");
	int nowpage = ((Integer)request.getAttribute("page")).intValue();
	int maxpage = ((Integer)request.getAttribute("maxpage")).intValue();
	int startpage = ((Integer)request.getAttribute("startpage")).intValue();
	int endpage = ((Integer)request.getAttribute("endpage")).intValue(); 
  %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="./board_css/board_view.css"/> 
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<script language = "javascript">
function addcomment() {
	commentform.submit();
}
</script>
</head>
<body>
<div class="header">
  <h2> B o o k </h2>
</div>
 <div id ="all">
	<h3 id="title"><%=board.getBOARD_TITLE() %></h3>
	<h5 id="title1">By <%= board.getBOARD_ID()%> ㆍ <%=board.getBOARD_DATE() %></h5>
<div class ="row">
	<div class ="leftcolumn">
		<div class="card1">
			<div>
		<%if(!(board.getBOARD_IMAGE()==null)) {%>
		<img align="center" class = "getimage" src="./boardupload/<%= board.getBOARD_IMAGE() %>" >
		<%} %>
		<p class="content"> <%= board.getBOARD_CONTENT() %></p>
			</div> 
		</div>
		
		<!-- 목록 버튼 start-->
		<div id = "buttongroup">
		<% if(sess == null){ %>
			<div align="center">
			<button type="button" onclick="location.href='./BoardList.bo'" class="btn">목록</button>
			</div>
			<%}else if(sess.equals(board.getBOARD_ID())){%>
			<div id="buttons" align="center">
			<button type="button" onclick="location.href='./BoardList.bo'" class="btn">목록</button>
			<button type="button" onclick="location.href='./BoardModify.bo?num=<%=board.getBOARD_NUM() %>'" class="btn">수정</button>
			<button type="button" onclick="location.href='./BoardDeleteAction.bo?num=<%=board.getBOARD_NUM() %>'" class="btn">삭제</button>
			<button type="button" onclick="location.href='./BoardLike.bo?num=<%= board.getBOARD_NUM() %> '" class="btn">좋아요</button>
			</div>
			<% } else {%>
			<div id="buttons" align="center">
			<button type="button" onclick="location.href='./BoardLike.bo?num=<%= board.getBOARD_NUM() %> '" class="btn">좋아요</button>
			<button type="button" onclick="location.href='./BoardList.bo'" class="btn">목록</button>
			</div>
			<% } %>
		</div>
		<!-- 목록 버튼 end -->
		
		<!-- 입력 출력 start -->
		
		<form action="./CommentListAction.bo" method="post" name="commentform">
		<% if(session.getAttribute("id")!=null){ %>
		<section class="mb-5 mt-3">
			<div class="card bg-light">
				<div class="card-body">
					<div><input type="hidden" name="BOARD_NUM" value="<%= board.getBOARD_NUM() %>"/></div>
					<input name="COMMENT1_ID" type="hidden" value="<%= sess %>">
					<div>
					<form class="fw-blod mb-4">
					<div class="comment">
					<textarea class="form-control" name="COMMENT1_CONTENT" rows="3"  style="resize: none;"></textarea>
				<button type="button" onclick="location.href='javascript:addcomment()'" ><img src="./img/edit.png"></button>
					</div>
					</form>
					</div>
					<%} %>
					
					
					<div class="ms-3">
					
		<%
		for(int i = 0; i<commentList.size(); i++) {
			CommentBean cm=(CommentBean)commentList.get(i);		
	 	%>
		<hr color="#ccc">
	 	<div class="d-flex mb-4 mt-3">
	 	<div class="ms-3">
						<div class="fw-bold"><%=cm.getCOMMENT1_ID() %></div>
						<div><%=cm.getCOMMENT1_CONTENT() %></div>
				</div>
				<div>
			<%if(sess!=null && sess.equals("admin")) {%>
		<button type="button" onclick="location.href='./CommentDeleteAction.bo?num=<%= cm.getCOMMENT1_NUM() %>&bnum=<%=board.getBOARD_NUM()%>'"><img src="./img/trash.png"></button>
		
		<% } %>
		</div>
	 	</div>
	 	<%} %>
			</div>
		</section>
		
	
	 <!-- 입력 출력 end -->
	 
	 <!--  이전 이후 버튼 start-->
	 <div class="pagination">
	 <% if(nowpage <= 1) {%>
	 	<img src="./img/free-icon-previous-318276 (1).png"/>
	 		<% } else { %>
	 <a href="./BoardDetailAction.bo?page=<%= nowpage - 1 %>" class="prev no-more-prev">
		<img src="./img/free-icon-previous-318276 (1).png"/>
	</a>
	 <% } %>
	 
	 <% for (int a = startpage; a <= endpage; a++) {
		if(a == nowpage) { %>
	   &nbsp;<%= a %>&nbsp;
	   <% } else { %>
	   &nbsp;<a href="./BoardDetailAction.bo?page=<%= a %>"><%= a %></a>&nbsp;
		<% } %>
	<% } %>
	
	<% if (nowpage >= maxpage) { %>
	 	<img src="./img/free-icon-next-158735 (1).png"/>
	<% } else { %>
	 <a href="./BoardDetailAction.bo?page=<%= nowpage + 1 %>" class="next">
	 	<img src="./img/free-icon-next-158735 (1).png"/>
	 </a>
	 <% } %>
	 </div>
	 <!-- 이전 이후 버튼 end -->
	</form>
	</div>

</div>
</div>

</body>
</html>
