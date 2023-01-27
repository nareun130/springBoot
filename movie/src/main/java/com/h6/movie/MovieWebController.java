package com.h6.movie;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

// url : /movie로 접속할 때 페이지 넘겨주는 controller 
//영화목록 조회, 추가, 삭제, 관리자 모드로 가는 것 까지 처리해줄 컨트롤러
@Controller
@RequestMapping("/movie")
public class MovieWebController {

	// session에 넣어줄 속성이름 설정
	final String LOGIN_MEMBER = "loginMember";

	final MovieDAO dao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 프로퍼티 파일로부터 저장 경로 참조
	@Value("${movie.imgdir}")
	String fdir;

	@Autowired
	public MovieWebController(MovieDAO dao) {
		this.dao = dao;
	}

	// 영화 추가
	@PostMapping("/add")
	public String addMovie(@ModelAttribute Movie movie, Model m, @RequestParam("file") MultipartFile file) {

		System.out.println("movie.getMid: " + movie.getMid());
		try {

			// 저장 파일 객체 생성
			File dest = new File(fdir + "/" + file.getOriginalFilename());
			// 파일 저장
			file.transferTo(dest);

			// Movie 객체에 파일 이름 저장
			movie.setImg("/img/" + dest.getName());// application.properties와 비교하면서 보면 좋을 듯

			dao.addMovie(movie);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("영화 추가 과정에서 문제 발생!!");
			m.addAttribute("error", "영화가 정상적으로 등록되지 않았습니다!!");
		}

		// url방식으로 쏴준다.
		return "redirect:/movie/admin";//영화 등록 후 관리자의 영화 관리 페이지로 쏴준다.
	}

	//영화 삭제
	@GetMapping("/delete/{mid}")
	public String deleteNews(@PathVariable int mid, Model m) {
		try {
			dao.delMovie(mid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("영화 삭제 과정에서 문제 발생!!");
			m.addAttribute("error", "영화가 정상적으로 삭제되지 않았습니다!!");
		}

		//영화 삭제후 바로 영화관리 목록으로 되돌아옴
		return "redirect:/movie/admin";
	}

	@GetMapping("/admin")
	public String adminMovie(Model m, HttpServletRequest request) {

		List<Movie> list;
		// 적용이 안된다.
		try {
			list = dao.getAll();
			m.addAttribute("movielist", list);
			m.addAttribute("admin", 1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("영화 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "영화 목록이 정상적으로 처리되지 않았습니다!!");
		}
		// 바로 jsp로 쏴준다.
		return "movie/movieList";
	}

	//영화 목록 선택했을 시 영화 목록이 나올 수 있도록
	@GetMapping("/list")
	public String listMovie(Model m, HttpServletRequest request) {
		List<Movie> list;
		try {
			list = dao.getAll();
			m.addAttribute("movielist", list);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "movie/mainList";
	}

	//영화 선택했을 시 세부사항으로 이동
	@GetMapping("/{mid}")
	public String getMovie(@PathVariable int mid, Model m) {

		try {
			Movie movie = dao.getMovie(mid);
			//detail.jsp에서 나중에 mylist의 mid와 비교를 위해서 
			/*나중에 다른 컨트롤러에서 mylist라는 속성추가하면 mylist속성의 mid와 
			 * movie속성의 mid를 비교하여
			 * detail.jsp에서 위시리스트 추가버튼이 뜰 지 안뜰지 정해주려고
			 * */
			m.addAttribute("movie", movie);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("영화를 가져오는 과정에서 문제 발생!!");
			m.addAttribute("error", "영화를 정상적으로 가져오지 못했습니다!!");
		}

		// 영화 상세보기로 이동
		return "movie/detail";
	}
}
