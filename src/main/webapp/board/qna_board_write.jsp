<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<% String id = (String)session.getAttribute("id"); %>
<!DOCTYPE html>
<html>
<head>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./resources/boardcss/boardwrite.css" rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>MVC 게시판</title>
<script language="javascript">
function addboard(){
	boardform.submit();
}</script>
</head>
<body>
<div class="header">
<h2>B o o k</h2>
<div class="instruction" id="username"><%= id %></div>
<hr>
</div>
<form action="./BoardAddAction.bo" method="post" enctype="multipart/form-data" name="boardform" class="whole-form">
<input type="hidden" name="BOARD_ID" value="<%= id %>">
<div class="info-input">
<input type="text" name="BOARD_TITLE" placeholder="제목"></input>
</div>
<div class="info-input">
<textarea name="BOARD_CONTENT" class="content-area"></textarea>
</div>
<div class="info-input">
  <input name="BOARD_IMAGE" type="file" class="file-upload">
</div>
<div class="buttons">
<a href="javascript:addboard()"><button type="button" class="btn btn-secondary btn-sm">
등록</button></a>
<a href="javascript:history.go(-1)"><button type="button" class="btn btn-secondary btn-sm">
뒤로</button></a>
</div>
</form>
</body>
</html>