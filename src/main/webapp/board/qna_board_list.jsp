
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="net.board.db.*" %>

<%
	String id = null;
	if(session.getAttribute("id")!= null){
		id = (String)session.getAttribute("id");
	}
	List boardList = (List)request.getAttribute("boardlist");
	int listcount=((Integer)request.getAttribute("listcount")).intValue();
	int nowpage=((Integer)request.getAttribute("page")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
	int startpage=((Integer)request.getAttribute("startpage")).intValue();
	int endpage=((Integer)request.getAttribute("endpage")).intValue();
		
		// 검색 기능용
	String srchKey = (String)request.getAttribute("srchKey");
	if(srchKey == null) {
	   srchKey = "";
	}

	String srchFlds = (String)request.getAttribute("srchFlds");
	if(srchFlds == null) {
	   srchFlds = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>

<title>BLOG Project</title>
<script type="text/javascript">
function submitSrchForm() {
   document.srchForm.srchKey.value = document.srchForm.srchKey.value.trim();
   // .trim() == 좌우 공백을 없애주는것
   document.srchForm.submit();
}
function resetSrchForm() {
   document.srchForm.srchFlds[0].selected = true;
   document.srchForm.srchKey.value = "";
}
</script>
<style>
.container {
display : grid; 
grid-auto-flow:row;
grid-template-columns: repeat(4, 1fr);
grid-gap : 20px;
}
body{
padding : 100px 30px 30px 30px;
} 

.card-body{
padding:3px;
  display : flex;
  justify-content: space-between;
}
font{
font-size:13px;
color : gray;}

.card-img-top{
height : 25rem;
object-fit:cover;}
.srchbtn{
display:flex;}
.srchfd{
display:flex;
 justify-content: space-between;}
</style>
</head>

<body>
<nav class="navbar navbar-light bg-light fixed-top">
  <div class="container-fluid">
    <a class="navbar-brand" href="./BoardList.bo"><h3 font-size="40px">B o o k</h3></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
      <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasNavbarLabel">B o o k</h5>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div class="offcanvas-body">
        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="./BoardList.bo">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./SuggestList.bo">추천게시판</a>
          </li>
          <li class="nav-item">
          <% if(session.getAttribute("id")== null){ %>
            <a class="nav-link" href="./MemberLogin.me">로그인</a>
		<%} %>
          </li>
          <li class="nav-item">
          	<% if(session.getAttribute("id") != null){ %>
            <a class="nav-link" href="./MemberLogout.me">로그아웃</a>
			<%} %>
          <hr>
          <h6 >책 검색 </h6>
        </ul>
  		 <form name="srchForm" action="BoardList.bo" method="post">
   <select name="srchFlds" class="srchFields">
      <option value="all" <%= srchFlds.equals("all") ? "selected='selected'" : "" %> >모두</option>
      <option value="sub" <%= srchFlds.equals("sub") ? "selected='selected'" : "" %> >제목</option>
      <option value="con" <%= srchFlds.equals("con") ? "selected='selected'" : "" %> >내용</option>
      </select>
      	<div class="srchbtn">
        <input class="form-control me-2" type="text" name="srchKey" placeholder="검색어를 입력하세요" value="<%=srchKey %>" aria-label="Search">
          <button type="button" class="btn btn-secondary btn-sm" onclick="submitSrchForm()">Search</button>
          <button type="button" class="btn btn-secondary btn-sm" onclick="resetSrchForm()">Reset</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</nav>
	<%if(id!=null){ %>
	<!-- 글쓰기 버튼 -->
	<div>
	<div>
	<a href="./BoardWrite.bo"><img src="./img/notebook.png"></a><font>글 작성</font></div>
	<%} %>
	<br>

<div class="container">
<% 
	for(int i = 0; i < boardList.size(); i++){
		BoardBean bl = (BoardBean)boardList.get(i);
%>
    
    <div class="card" style="width: 18rem;">
    <div class="card_img">
    <a href="./BoardDetailAction.bo?num=<%=bl.getBOARD_NUM()%>">
  <img src="./boardupload/<%=bl.getBOARD_IMAGE() %>" class="card-img-top" alt="..."></a>
  </div>
  <h5 class="card-title">&nbsp;<%= bl.getBOARD_TITLE() %> / <%= bl.getBOARD_ID() %></h5>
  <div class="card-body">
 		<div><%= bl.getBOARD_DATE() %></div>
  		<div>
		<img src="./img/thumbs-up.png"> &nbsp;&nbsp;
		<%= bl.getBOARD_LIKE() %></div>
  </div>
</div>
<%} %>

</div><br/>

<!-- 페이지 버튼 -->
<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-end">
<% if(nowpage <= 1){ %>
    <li class="page-item disabled">
      <div class="page-link">Previous</div></li>
<%} else { %>
      <li><a class="page-link" href="./BoardList.bo?page=<%=nowpage-1 %>" tabindex="-1" aria-disabled="false">Previous</a>
    </li>
<% } %>
<% for(int a = startpage; a <= endpage; a++){
	if( a == nowpage){%>
    <li class="page-item disabled"><div class="page-link"><%= a %></div></li>
	<%}else{ %>
    <li class="page-item"><a class="page-link"  href="./BoardList.bo?page=<%= a %>"><%= a %></a></li>
	<%} %>
	<%} %>
	<%if (nowpage >= maxpage) {%>
      <li class="page-item disabled"><div class="page-link">Next</div></li>
	<%}else{ %>
      <li><a class="page-link" href="./BoardList.bo?page=<%= nowpage+1 %>">Next</a></li>
	<% } %>
  </ul>
</nav>
</div>
</body>
</html>