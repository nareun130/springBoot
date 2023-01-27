<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>header</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Dongle:wght@300&family=Gaegu&family=Gowun+Dodum&display=swap" rel="stylesheet">
<style>
input {
	transform: rotate(-0.03deg);
}

body {
	margin: 0px;
	font-family: 'Gaegu', cursive;
	box-sizing: border-box;
	background-color: blanchedalmond;
}

div, input, textarea {
	box-sizing: border-box;
}

a {
	text-decoration: none;
	color: black;
}

.container {
	display: block;
	margin-left: auto;
	margin-right: auto;
	width: 1000px;
	background-image: url("img/헤더이미지.png");
	background-position: lefttop;
}

.header-container {
	background-color: skyblue;
	display: flex;
	height: 80px;
	align-items: center;
	padding: 10px;
}

.header-main {
	margin-left: 25px;
	letter-spacing: 4px;
	font-weight: bold;
	cursor: pointer;
}

.header-main a {
	color: white !important;
	font-size: 40px !important;
	font-family: "Black Han Sans", sans-serif !important;;
}

.header-sub {
	margin-left: -20px;
	color: white;
	font-size: 30px;
	font-family: 'Gaegu', cursive;
}

.header-sub a {
	color: whit e;
}

.header-item {
	margin-right: 25px;
	cursor: pointer;
}

.header-item a {
	color: white;
	font-size: 30px;
	font-weight: bold;
	font-family: "Dongle", sans-serif;
}
</style>
</head>
<body>
  <header class="header-container">
    <div class="header-item header-main">
      <a href="/">H6 시네마</a>
    </div>

    <c:choose>
      <c:when test="${loginMember==null}">
        <div class="header-item">
          <a href="/movie/list">영화목록</a>
        </div>
      </c:when>
      <c:otherwise>
        <div class="header-item">
          <a href="/mylist/${loginMember }">마이리스트</a>
        </div>
      </c:otherwise>
    </c:choose>
    <div style="flex-grow: 1"></div>

    <c:choose>

      <c:when test="${loginMember==null}">
        <div class="header-item">
          <a href="/member/login"> 로그인 &nbsp; |</a>
        </div>
        <div class="header-item">
          <a href="/member/register">회원가입 &nbsp;</a>
        </div>
      </c:when>
      <c:otherwise>
        <div class="header-item">
          <a>${loginMember}님환영합니다.&nbsp; </a>
        </div>
        <div class="header-item">
          <a href="/member/logout">| 로그아웃</a>
        </div>

      </c:otherwise>
    </c:choose>

  </header>

</body>
</html>