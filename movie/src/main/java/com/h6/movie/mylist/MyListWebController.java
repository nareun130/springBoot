package com.h6.movie.mylist;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.h6.movie.Movie;
import com.h6.movie.MovieDAO;


@Controller
@RequestMapping("/mylist")
public class MyListWebController {
	//url : /mylist로 들어오는 요청들을 처리해주는 컨트롤러
	
	final String LOGIN_MEMBER = "loginMember";
	final MyListDAO dao;//MyList에서 값을 가져올 DAO객체

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MyListWebController(MyListDAO dao) {
		this.dao = dao;
	}


	//url : /mylist/{userid}로 들어오는 요청 처리
	//접속한 userid의 마이리스트목록을 불러와줌
	@GetMapping("/{userid}")
	public String myList(Model m, HttpSession session) {

		
		Map<Integer, String> mylist;//mid와 title을 가지고 있을 mylist map
		String userid;
		//session에서 접속한 userid속성값을 String으로 형변환해서 가져와줌
		//왜 why? session.getAttribute()는 Object를 리턴해주니까!
		userid = (String) session.getAttribute(LOGIN_MEMBER);
		
		//접속한 userid에 해당하는 mylist Treemap<mid,title>을 가져옴
		mylist = dao.getAll(userid).getMovies();
		//페이지의 세션에 mylist속성값 추가
		m.addAttribute("mylist", mylist);
		return "mylist/mylist";
	}

	//mylist에서 영화 삭제
	@GetMapping("/delete/{mid}")
	public String getMyMovie(@PathVariable("mid") int mylistMid, HttpSession session) {
		
		//mylist를 반영할 map 객체
		Map<Integer, String> mylist;
		String userid;
		
		userid = (String) session.getAttribute(LOGIN_MEMBER);
		//mylistMid에 해당하는 값(db에서 mylist테이블의 mid칼럼값)을 가진 행을 삭ㅈ[
		
		dao.delMyList(userid, mylistMid);
		
		
		//삭제하고 나서 마이리스트 변경을 실시간으로 반영하기 위해 다시 mylist를 가져와줌
		mylist = dao.getAll(userid).getMovies();
		//세션의 값에 mylist속성을 추가
		session.setAttribute("mylist", mylist);

		//mylist로 redirect해줌.
		return "redirect:/mylist/mylist";

	}

	/*
	 * url : /mylist/view/{mid}로 설정 원래는 url을 /mylist/{mid}로 설정하려 했었다.
	 * 하지만 @GETMapping("{mid}")가 MovieWebController에 이미 존재하므로 Spring이 매핑할 때 혼동해서
	 * 페이지를 못 찾으므로 /view/{mid}로 해줌
	 */
	@GetMapping("/view/{mid}")
	public String getMyMovie(@PathVariable("mid") int mylistMid, Model m) {
		//mylist.jsp에서 속성name=mid인 값을 mylistMid변수로 받아온다.
		try {
			Movie movie = dao.getMovie(mylistMid);
			m.addAttribute("movie", movie);
			
		} catch (Exception e) {
			System.err.println(this.getClass().getName() + "의 getMyMovie에서 에러 발생 : " + e.getMessage());
		}
		return "mylist/detail";

	}
	
	@GetMapping("/add/{mid}")
	public String addMyMovie(@PathVariable("mid") int mylistMid, HttpSession session) {
		
		//mylist.jsp에서 속성name=mid인 값을 mylistMid변수로 받아옴
		String userid;
		
		//1. 실시간으로 mylist를 보여주기 위해 mylist에다 값 추가해주고 다시불러와서
		userid = (String) session.getAttribute(LOGIN_MEMBER);
		dao.addMyList(userid, mylistMid);

		Map<Integer, String> mylist;
		mylist = dao.getAll(userid).getMovies();
		//2. session에 값 저장
		session.setAttribute("mylist", mylist);

		//redirect로 다시 mylist.jsp로 보내줌.
		return "redirect:/mylist/mylist";
	}

}
