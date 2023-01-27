<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>무비리스트</title>
<style>
* {
	box-sizing: border-box;
}

body {
	font-family: "돋움";
	font-size: 12px;
	color: #444444;
}

ul {
	list-style-type: none;
}

li {
	list-style-type: none;
	font-size: 25px;
}

.title {
	text-align: center;
}

.clear {
	clear: both;
}

div .movie {
	display: inline-block;
	width: 18%;
	height: 10%;
}

img {
	width: 100%;
	height: 100%;
}

/* #movielist .movie img { */
/* 	float: left; */
/* 	margin-top: 20px; */
/* 	text-align: center; */
/* 	width: 200px; */
/* 	height: 100px; */
/* } */
.plus {
	font-size: 30px;
}

a {
	text-decoration: none;
}

#movielist h3 {
	padding-bottom: 5px;
	margin-left: 35px;
	/* 	border-bottom: 2px solid dodgerblue; */
}

.plus {
	color: white;
	background-color: gray;
	border-radius: 5px;
	width: 25px;
	height: 25px;
}
</style>
</head>

<body>
  <header>
    <%@ include file="header.jsp"%>
  </header>
  <div id="movielist">
    <!--     <h3>영화목록</h3> -->
    <c:forEach var="movie" varStatus="status" items="${movielist }">

      <div class="movie">
        <ul>
          <li><a href="/movie/${movie.mid }"><img src="${movie.img}" alt="${movie.title }"></a> <c:if test="${loginMember!=null }">
              <%--               <span> <a href="/mylist/add/${movie.mid }" class="plus">+</a></span> --%>
            </c:if></li>
          <li class="title">${movie.title }</li>
        </ul>

      </div>


      <!--       <div class="clear"></div> -->
    </c:forEach>

  </div>
  <footer>
    <%@ include file="footer.jsp"%>
  </footer>
</body>

</html>