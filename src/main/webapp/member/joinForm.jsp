<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="./resources/membercss/join.css" rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>회원가입 페이지</title>
</head>
<body>

<div class="joinform-whole">
<div class="content-box">
<form name="joinform" action="./MemberJoinAction.me" method="post" class="input-area">
<div class="input-caption">아이디</div>
<input type="text" class="input-field" placeholder="아이디" name="MEMBER_ID" required>
<div class="input-caption">패스워드</div>
<input type="password" class="input-field" placeholder="비밀번호" name="MEMBER_PW" required>
<div class="input-caption">이름</div>
<input type="text" class="input-field" placeholder="이름" name="MEMBER_NAME" required>
<div class="input-caption">성별</div>
<input type="radio" name="MEMBER_GENDER" value="남" style="color:white;" checked/>남자
<input type="radio" name="MEMBER_GENDER" value="여" style="color:white;" />여자
<div class="input-caption">이메일</div>
<input type="email" class="input-field" placeholder="이메일" name="MEMBER_EMAIL" required>
<a href="javascript:joinform.submit()"><button class="submit">가입 완료</button></a>
</form>
</div>
</div>

</body>
</html>