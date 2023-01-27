package com.h6.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.h6.movie.Movie;
import com.h6.movie.MovieDAO;

//메인페이지로 보내주기 위한 컨트롤러
@Controller
public class MainController {

	// DB에 접속해 Movie테이블의 행들을 Movie객체로 주고받을 MovieDAO클래스
	final MovieDAO dao;// MovieDAO에 @Component가 있기 때문에 Spring Container에 자동으로 객체가 생성되어 올라가 있음.

	// MainController 생성되면서 자동으로 MovieDAO객체 자동 주입 -> @Autowired로 인해
	@Autowired
	public MainController(MovieDAO dao) {
		this.dao = dao;
	}

	// url : "/"를 통해 이동됨 -> localhost:9000/를 입력해 접속 가능
	@GetMapping("/")
	public String index(Model m) {
		// dao로부터 list를 가져옴
		List<Movie> list = dao.getAll();

		// Model 객체에 정보를 movielist정보를 담는다.
		/*
		 * JSP Servlet에서 request나 session내장객체에 정보를 담아 jsp에 넘겨준다. 
		 * Spring에서는 Model이 이런 역할을 한다.
		 */
		m.addAttribute("movielist", list);

		// index.jsp로 보내준다.
		return "index";
	}
}
