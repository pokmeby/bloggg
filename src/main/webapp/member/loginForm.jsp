<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="./resources/membercss/login.css" rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
<body>
<form name="loginform" action="./MemberLoginAction.me" method="post">
    <div class="page-container">
        <div class="login-form-container shadow">
            <div class="login-form-right-side">
                <div class="top-logo-wrap">
                   
                </div>
                <h1>Leaders are Readers</h1>
                <p>현재에 고통은 잠깐이다.    하지만 포기는 영원히 남는다</p>
            </div>
            <div class="login-form-left-side">
                <div class="login-top-wrap">
                    <a href="MemberJoin.me"> <input type="button" value="회원가입" class="create-account-btn shadow-light" id="click"></a>
                </div>
                <div class="login-input-container">
                    <div class="login-input-wrap input-id">
                        <i class="far fa-envelope"></i>
                        <input placeholder="아이디" type="text" name="MEMBER_ID">
                    </div>
                    <div class="login-input-wrap input-password">
                        <i class="fas fa-key"></i>
                        <input placeholder="비밀번호"  type="password" name="MEMBER_PW">
                    </div>
                </div>
                <div class="login-btn-wrap">
                    <a href="javascript:loginform.submit()"><button class="login-btn">Login</button></a>
                </div>
            </div>
        </div>
    </div>
    </form>
</body>

<!-- 
<table border=1>
	<tr>
		<td colspan="2" align=center>
			<b><font size=5>로그인 페이지</font></b>
		</td>
	</tr>
	<tr><td>아이디 : </td><td><input type="text" name="MEMBER_ID"/></td></tr>
	<tr><td>비밀번호 : </td><td><input type="password" name="MEMBER_PW"/></td></tr>
	<tr>
		<td colspan="2" align=center>
			<a href="javascript:loginform.submit()">로그인</a>&nbsp;&nbsp;
			<a href="MemberJoin.me">회원가입</a>&nbsp;&nbsp;
		</td>
	</tr>
</table> -->

</body>
</html>