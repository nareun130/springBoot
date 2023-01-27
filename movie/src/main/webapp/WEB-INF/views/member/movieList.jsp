<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
<!--  메인 해더 가져오기 -->
<!-- my CSS -->
<link href="../css/style.css" rel="stylesheet">

<!--google font-->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gowun+Dodum&family=Do+Hyeon&family=Rowdies:wght@300;400;700&display=swap"
	rel="stylesheet">
<!--google icons-->
<link
	href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined"
	rel="stylesheet">
<!-- font awesome -->
<link href="../fontAwesome/css/all.min.css" rel="stylesheet">
<!--  요기까지  -->

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
	crossorigin="anonymous">
	
</script>
<style>
/*
	li 배치 가로로
	*/
</style>


<title>영화 관리 등록 리스트</title>
</head>
<body>
	<header>
		<%@ include file="header.jsp"%>
	</header>
	<hr />

	<div class="container w-75 mt-5 mx-auto">
		<h1>영화 관리 등록</h1>
		<hr />
		<ul id='list-group'>
			<c:forEach var="movie" items="${movielist}" varStatus="status">
				<li
					class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"><a
					href="/movie/${movie.mid}" class="text-decoration-none">
						${status.count}) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${movie.title} </a> <a
					href="/movie/delete/${movie.mid}"><span
						class="badge bg-secondary">&times;</span></a></li>
			</c:forEach>
		</ul>



		<!-- 수정 안해도 됨. -->
		<button class="btn btn-outline-info mb-3" type="button"
			data-bs-toggle="collapse" data-bs-target="#addForm"
			aria-expanded="false" aria-controls="addForm">영화 등록</button>


		<div class="collapse" id="addForm">
			<div class="card card-body">
				<form method="post" action="/movie/add"
					enctype="multipart/form-data">
					<label class="form-label">영화 제목</label> <input type="text"
						name="title" class="form-control" /> <label class="form-label">감독</label>
					<input type="text" name="director" class="form-control" /> <label
						class="form-label">배우</label> <input type="text" name="actor"
						class="form-control" /> <label class="form-label">개봉일</label> <input
						type="date" name="cdate" class="form-control" /> <label
						class="form-label">이미지</label> <input type="file" name="file"
						class="form-control" /> <label class="form-label">상영시간</label> <input
						type="number" name="rtime" class="form-control" /> <label
						class="form-label">즐거리</label>
					<textarea cols="50" rows="5" name="content" class="form-control">
					</textarea>
					<button type="submit" class="btn btn-success mt-3">등록</button>
				</form>
			</div>
		</div>
	</div>
	<hr />
	<footer>
		<%@ include file="footer.jsp"%>

	</footer>
</body>
</html>