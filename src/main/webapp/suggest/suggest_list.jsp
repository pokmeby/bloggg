<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "net.member.db.*" %>
<% String id = (String)session.getAttribute("id"); %>

<%
	MemberBean member = (MemberBean)request.getAttribute("memberdata");
	SuggestBean suggest = (SuggestBean)request.getAttribute("suggestdata");
%>

<%
	List suggestList =(List)request.getAttribute("suggestlist");
	int listcount = ((Integer)request.getAttribute("listcount")).intValue();
	int nowpage = ((Integer)request.getAttribute("page")).intValue();
	int maxpage = ((Integer)request.getAttribute("maxpage")).intValue();
	int startpage = ((Integer)request.getAttribute("startpage")).intValue();
	int endpage = ((Integer)request.getAttribute("endpage")).intValue();
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">

<title> 추천 게시판 </title>
<!-- 추천 게시판 -->
<script language = "javascript">
function addsuggest() {
	suggestform.submit();
}
</script>
<style>

#title {
    font-size:25px; 
}
#id_nick{
		border : 1px solid black;
}
#recommend_comment{

	border: 1px solid;
}
.comment{
display : flex;
}
#comment_id{
}
#comment_content{
}
#comment_date{
}
#comment_delete{
position: absolute;
right:30px;
}
.h2{
	height : 2px;
	background: #ccc;
}
.h1{
	height : 1px;
	background: #ccc;
}
.mt-3 {
  margin-top: 1rem !important;
}
.ms-3 {
  margin-left: 1rem !important;
}
.mb-4 {
  margin-bottom: 1.5rem !important;
}
.mb-5 {
  margin-bottom: 3rem !important;
}
.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 1px solid rgba(0, 0, 0, 0.125);
  border-radius: 0.25rem;
}

.bg-light {
  --bs-bg-opacity: 1;
  background-color: rgba(var(--bs-light-rgb), var(--bs-bg-opacity)) !important;
}

.card-body {
  flex: 1 1 auto;
  padding: 1rem 1rem;
}

.in_nick-control {
  display: block;
  width: 50%;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #212529;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.form-control {
  display: flex;
  width: 100%;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #212529;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.form-control::-moz-placeholder {
  color: #6c757d;
  opacity: 1;
}
.form-control:-ms-input-placeholder {
  color: #6c757d;
  opacity: 1;
}
.form-control::placeholder {
  color: #6c757d;
  opacity: 1;
}

.d-flex {
  display: flex !important;
  justify-content: space-between;
}
.fw-bold {
  font-weight: 700 !important;
}

.delete_comment{
	float: right;
}


.btn {
	display: inline-block;
    width: 60px;
    height: 36px;
background-color: #333;
    text-align: center;
    font-weight: 400;
    font-size: 14px;
    line-height: 36px;
    color: #fff;
    vertical-align: middle;
    margin-right: 5px;
}

</style>
</head>
<body>





<form action="./SuggestListAction.me" method="post" name="suggestform">
<!--  입력  -->
<div id="title" align="center">
<h2>추천 게시판</h2>
</div>
<button type="button" onclick="location.href='./BoardList.bo'" class="btn">목록</button>
<!-- 입력 -->
		


	
<!-- 출력 -->

<section class="mb-5">
	<div class="card bg-light">
		<div class="card-body">
			<!-- Comment form-->
<form class="mb-4">
<div class="fw-bold"><% if(id!=null) { %>
		<input  name="SUGGEST_NICKNAME" type="hidden" value="<%= id %>"><%=id %>
		<% } else { %>	
		<input class="in_nick-control" name="SUGGEST_NICKNAME" type="text" placeholder="닉네임을 입력하세요" value=""/>
		<%} %><br>
</div>
		
<div class="comment" >
<textarea name="SUGGEST_CONTENT" class="form-control" rows="3" placeholder="Join the discussion and leave a comment!" style="resize: none;"></textarea>
<button type="button" onclick="location.href='javascript:addsuggest()'"><img src="./img/edit.png"></button>

<!-- <a href="javascript:addsuggest()"><img src="./img/edit.png"></a> -->
</div>
</form>
	<!-- Comment with nested comments-->
   <!-- Single comment-->
   
   
   
           	<%
		for(int i = 0; i<suggestList.size(); i++) {
			SuggestBean sl=(SuggestBean)suggestList.get(i);		
	 %>
<div class="d-flex mb-4 mt-3">
<div class="ms-3">
	 	<input type="hidden" name="SUGGEST_NUM" value="<%= sl.getSUGGEST_NUM() %>"/>
<div class="fw-bold"><%=sl.getSUGGEST_NICKNAME() %></div>
			<%= sl.getSUGGEST_CONTENT() %>
</div>
<div>
		<%=sl.getSUGGEST_DATE() %>
	
		<%if(id!=null && id.equals("admin")) {%>
			<a href="./SuggestDeleteAction.me?num=<%= sl.getSUGGEST_NUM()%>"><img src="./img/trash.png"></a>
		<% } %>
	</div>
	</div>

	<%
	} 
	%>
	</div>
</section>
	
<!-- 출력  -->	



	<!-- [이전] [다음] 버튼 -->
		
<div align="center" height="20">
	<div colspan="7" style="font-family:Tahoma;font-size:10pt;">
	<% if(nowpage <= 1) {%>
		<img src="./img/free-icon-previous-318276 (1).png">&nbsp;
	<% } else { %>
	<a href="./SuggestList.me?page=<%= nowpage - 1 %>"><img src="./img/free-icon-previous-318276 (1).png"></a>&nbsp;
	<% } %>
	
	<% for (int a = startpage; a <= endpage; a++) {
		if(a == startpage) { %>
	   [<%= a %>]&nbsp;
	   <% } else { %>
	   <a href="./SuggestList.me?page=<%= a %>">[<%= a %>]</a>
	   &nbsp;
		<% } %>
	<% } %>
	
	<% if (nowpage >= maxpage) { %>
		<img src="./img/free-icon-next-158735 (1).png">
	<% } else { %>
		<a href="./SuggestList.me?page=<%= nowpage + 1 %>"><img src="./img/free-icon-next-158735 (1).png"></a>
	<% } %>
</div>
</div>

</form>



</body>
<!-- 추천 게시판 -->
</html>