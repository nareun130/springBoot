package com.h6.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//구현하고 싶었지만 시간 부족 ㅠ
@RestController
@RequestMapping("/api/movie")
public class MovieApiController {
	final MovieDAO dao;

	@Autowired
	public MovieApiController(MovieDAO dao) {
		this.dao = dao;
	}

	// 뉴스 등록
	@PostMapping
	public String addMovie(@RequestBody Movie movie) {
		try {
			dao.addMovie(movie);
		} catch (Exception e) {
			e.printStackTrace();
			return "Movie API: Movie 등록 실패!!";
		}
		return "Movie API: Movie 등록됨!!";
	}

	// 뉴스 삭제
	@DeleteMapping("{mid}")
	public String delMovie(@PathVariable("mid") int mid) {
		try {
			dao.delMovie(mid);
		} catch (Exception e) {
			e.printStackTrace();
			return "Movie API: Movie 삭제 실패!!";
		}
		return "Movie API: Movie 삭제됨!!";
	}

	// 뉴스 목록
	@GetMapping
	public List<Movie> getMovieList() {
		List<Movie> MovieList = null;

		try {
			MovieList = dao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MovieList;
	}

	//  상세 정보
	@GetMapping("{mid}")
	public Movie getNews(@PathVariable("mid") int mid) {
		Movie movie = null;

		try {
			movie = dao.getMovie(mid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movie;
	}
}
