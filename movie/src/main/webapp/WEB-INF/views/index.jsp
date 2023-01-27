<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8" />
<title>H6시네마</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
<!-- Link Swiper's CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.css" />

<!-- Demo styles -->
<style>
html, body {
	position: relative;
	height: 100%;
}

body {
	background: #eee;
	font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
	font-size: 14px;
	color: #000;
	margin: 0;
	padding: 0;
}

.swiper {
	width: 100%;
	padding-top: 50px;
	padding-bottom: 50px;
}

.swiper-slide {
	background-position: center;
	background-size: cover;
	width: 300px;
	height: 300px;
}

.swiper-slide img {
	display: block;
	width: 100%;
}
</style>
</head>

<body>
	<header>
		<!--   같은 폴더 위치에 있는 header를 가져옴 -->
		<%@include file="header.jsp"%>
	</header>
	<!-- Swiper -->
	<div class="swiper mySwiper">
		<div class="swiper-wrapper">
			<c:forEach var="movie" items="${movielist }" varStatus="status">
				<div class="swiper-slide">
					<a href="/movie/${movie.mid }"><img src="${movie.img }" alt=""></a>
				</div>
			</c:forEach>

		</div>
	</div>
	<div class="swiper-pagination"></div>


	<!-- Swiper JS -->
	<script src="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.js"></script>

	<!-- Initialize Swiper -->
	<script>
		var swiper = new Swiper(".mySwiper", {
			effect : "coverflow",
			grabCursor : true,
			centeredSlides : true,
			slidesPerView : "auto",
			coverflowEffect : {
				rotate : 50,
				stretch : 0,
				depth : 100,
				modifier : 1,
				slideShadows : true,
			},
			pagination : {
				el : ".swiper-pagination",
			},
		});
	</script>
	<footer>
		<!--   같은 폴더 위치의 footer.jsp을 가져옴 -->
		<%@ include file="footer.jsp"%>
	</footer>
</body>
</html>
