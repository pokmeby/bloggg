<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "net.board.db.*" %>
<%String id=(String)session.getAttribute("id") ;
BoardBean board = (BoardBean)request.getAttribute("boarddata");
%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./resources/boardcss/boardwrite.css" rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>MVC 게시판 수정</title>
<script type="text/javascript">
function modifyboard(){
	modifyform.submit();
}
</script>
</head>
<body>
<div class="header">
<h2>B o o k</h2>
<div class="instruction" id="username"><%= id %>
</div>
<hr>
</div>
<!--  게시판 수정 -->
<form action="BoardModifyAction.bo" method="post" enctype="multipart/fomr-data" name="modifyform" class="whole-form">
<input type="hidden" name="BOARD_ID" value="<%= board.getBOARD_ID() %>"/>
<input type="hidden" name="BOARD_NUM" value="<%= board.getBOARD_NUM() %>"/>
<div class="info-input">
<input name="BOARD_TITLE" type="text" placeholder="제목" value="<%= board.getBOARD_TITLE() %>"/>
</div>
<div class="info-input">
<textarea name="BOARD_CONTENT" class="content-area"><%=board.getBOARD_CONTENT() %></textarea>
</div>
<% if(!(board.getBOARD_IMAGE()==null)) { %>
<div class="info-input">
&nbsp;&nbsp;<%=board.getBOARD_IMAGE() %>
</div>
<% } %>
<div class="buttons">
<a href="javascript:modifyboard()"><button type="button" class="btn btn-secondary btn-sm">수정</button></a>&nbsp;&nbsp;
<a href="javascript:history.go(-1)"><button type="button" class="btn btn-secondary btn-sm">뒤로</button></a>
</div>
</form>
<!--  게시판 수정 -->
</body>
</html>