<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Login page</title>

<script type="text/javascript">
	
</script>
</head>
<style>
body {
	text-align: center;
	margin: 0 auto;
	border-radius: 25px;
	/* 	font-family: "맑은 고딕"; */
	font-size: 20px;
	color: black;
	text-align: center;
}

input[type=submit] {
	background-color: white;
	border: 1px solid black;
	font: 'Gaegu';
	border-radius: 5px;
/* 	width: 10%; */
}
input[type=submit]:hover{
	background-color: skyblue;
	color:white;
	border: 1px solid blanchedalmond;
}
</style>
<body>
	<header>
		<%@include file="header.jsp"%></header>
	<form action="/member/login" method="post">
		<h2>Login</h2>
		아이디 <br> <input type="text" name="userid"> <br /> 비밀번호 <br>
		<input type="password" name="userpw"> <br /> <br> <input
			type="submit" value="로그인"> <br />

	</form>
	<footer>
		<%@ include file="footer.jsp"%>
	</footer>
</body>
</html>