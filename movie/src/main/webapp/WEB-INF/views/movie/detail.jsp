<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화 상세정보</title>
<style>
body {
	margin: 0px;
	font-family: "맑은 고딕";
	box-sizing: border-box;
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
	/* 	margin-bottom: 20px; */
}

.header-container {
	display: flex;
	height: 80px;
	align-items: center;
	padding: 10px;
}

.header-main {
	margin-left: 25px;
	font-size: 30px !important;
	letter-spacing: 4px;
}

.header-sub {
	margin-left: -20px;
}

.header-item {
	margin-right: 25px;
	font-size: 20px;
	cursor: pointer;
}

.nav-container {
	display: flex;
	height: 40px;
	align-items: center;
	padding: 10px;
	justify-content: center;
}

.nav-item {
	margin: 60px;
	font-size: 20px;
	cursor: pointer;
}

.article-btn {
	padding: 15px;
	background: pink;
	/* 	border: dashed 1px palevioletred; */
	border-radius: 30px;
	left: 0;
	right: 0;
	margin: auto;
	width: 150px;
}

.movieinfo {
	list-style-image: url("../img/dot_blue.gif");
	font-size: 35px;
}

.article-container {
	width: 1000px;
	margin-top: 40px;
	font-size: 30px;
	margin-bottom: 500px;
}

.article-btn {
	background-color: pink;
	margin-left: 20px;
	font-size: 25px;
}

.article-img {
	float: left;
	width: 20%;
	margin-top: 20px;
	margin-left: 20px;
}

.float-l {
	font-size: 20px;
	float: left;
	/* 	text-align: left; */
}

.img-style {
	width: 80%;
	border-radius: 20px;
}

footer {
	clear: both;
	margin-top: 20px;
	/* 	margin-bottom: 20px; */
	/* 	border-top: solid 1px black; */
	/* 	background-color: rgb(247, 205, 234); */
}

.clear {
	clear: both;
}
</style>
</head>
<body>

  <header>
    <%@ include file="header.jsp"%>
  </header>
  
  
  <br>
  
  
  
  <c:choose>
    <c:when test="${admin==0 }">
      <%
      int temp = 0;
      %>
      <c:set var="temp" value="<%=temp%>" />

      <c:set var="mid" value="${movie.mid }" />
      <c:choose>
        <c:when test="${mylist ne null}">   <!-- ne= not equal -->
          <c:forEach var="mymovie" items="${mylist}" varStatus="status">
            <c:set var="mymid" value="${mymovie.key}" />
            <c:if test="${mymid ==mid}">
              <c:set var="temp" value="<%=1%>" />
            </c:if>
          </c:forEach>
          <c:if test="${temp ne 1 }">
            <a class="article-btn" href="/mylist/add/${mid}">마이리스트 추가</a>
          </c:if>
        </c:when>
        <c:otherwise>
          <a class="article-btn" href="/mylist/add/${mid}">마이리스트 추가</a>
        </c:otherwise>
      </c:choose>
    </c:when>
  </c:choose>
  
  
  <div class="clear"></div>
  <div>
    <div class="article-img">
      <img class="img-style" src="${movie.img }">
    </div>

  </div>

  <div>
    <ul class="movieinfo">
      <li class="detail" style="font-size: 45px; list-style-type: none">상세정보</li>
      <li>제목 : ${movie.title }</li>
      <li>감독 : ${movie.director }</li>
      <li>배우 : ${movie.actor }</li>
      <li>개봉일 : ${movie.cdate }</li>
      <li>상영시간 : ${movie.rtime }</li>
      <li>개요 : ${movie.content }</li>
    </ul>
  </div>
  <br>
  <div class="clear"></div>
  <br>

  <a class="article-btn" href="javascript:history.back()" style="background-color: skyblue">&lt;&lt;Back</a>



  <footer>
    <%@ include file="footer.jsp"%></footer>
</body>
</html>