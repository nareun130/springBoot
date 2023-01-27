package com.h6.member;

import java.net.http.HttpRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.h6.movie.mylist.MyListDAO;

@Controller
@RequestMapping("/member") // url : "/member"를 통해서 접속하게 해주는 controller
public class MemberWebController {
	final MemberDAO dao;
	final MyListDAO mydao;// 로그인 하면서 mylist를 가져오기 위해 mydao 선언
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MemberWebController(MemberDAO dao, MyListDAO mydao) {
		// 접속한 계정과 MyList를 보려고 두개의 객체를 같이 주입
		this.dao = dao;
		this.mydao = mydao;
	}

	// 회원가입 창으로 부터 넘어온
	@PostMapping("/add")
	public String addMember(@ModelAttribute Member member, Model m, @RequestParam("tel1") String tel1,
			@RequestParam("tel2") String tel2, @RequestParam("tel3") String tel3) {
		String tel = tel1 + tel2 + tel3;// 전화 번호 ex: 010-1111-1111입력 받으면 -> 01011111111로 바꿔줌.
		try {
			member.setTel(tel);// @ModelAttribute로 인해 register.jsp의 다른 name값들은 Member의 필드로 들어가고
			// 휴대폰 번호만 따로 설정
			dao.addMember(member);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("멤버 추가 과정에서 문제 발생 !!");
			m.addAttribute("error", "회원이 정상적으로 등록되지 않았습니다!!");
		}

		return "redirect:/";

	}

	// 가입창으로 갈 때
	@GetMapping("/register")
	public String register() {

		return "member/register";

	}

	// 그냥 로그인 안된 상태에서 로그인 들어갈 때
	@GetMapping("/login")
	public String login() {

		return "member/login";
	}

	// 로그인 창에서 로그인 시도할 때
	@PostMapping("/login")
	public String login(@ModelAttribute Member member, HttpServletRequest request) {

		final String LOGIN_MEMBER = "loginMember";
		final String ADMIN = "admin";
		// 로그인 성공했는지 실패했는지 확인
		int login = dao.allCheck(member.getUserid(), member.getUserpw());

//		로그인 하면서 mylist넣어주기 위해 변수선언
		Map<Integer, String> mylist;
		System.out.println("login 성공? : " + login);
		// 로그인 성공시
		if (login == 0 || login == 1) {
			// jsp페이지에 넘겨줄 멤버객체 생성해서 userid와 admin값을 set해줌
			// 왜 why? admin이 0인 멤버만 마이리스트를 볼수 있고, userid와 mid로 마이리스트 테이블을 구성하기 때문
			Member loginMember = new Member();
			loginMember.setUserid(member.getUserid());
			loginMember.setAdmin(login);

			// loginMember의 userid를 이용해 MyList의 treemap을 가져옴
			// 처음 가입한 회원은 마이리스트에 아무것도 없으므로 treemap의 size가 0
			mylist = mydao.getAll(loginMember.getUserid()).getMovies();

			HttpSession session = request.getSession();// session을 불러옴
			// session의 loginMember속성과 admin속성에 userid와 admin칼럼값을 넣어줌.
			// loginMember속성을 넣어주는 이유는 header에서 loginMember님 환영합니다.이렇게 나오기 위해서
			// admin속성을 넣어주는 이유는 header에서 관리자/회원 페이지를 나눠주기 위해
			session.setAttribute(LOGIN_MEMBER, loginMember.getUserid());
			session.setAttribute(ADMIN, loginMember.getAdmin());

			if (mylist.size() == 0) {
				// 읽어올때 size가 0이면 mylist에 null속성을 넣어줌.
				session.setAttribute("mylist", null);
			} else {
				// mylist에 mylist라는 List가 속성으로 들어감
				session.setAttribute("mylist", mylist);
			}

			// 일반회원일때
			if (login == 0) {
				// index.jsp페이지로 이동해줌.
				return "redirect:/";
			}

			// login이 1일 때 url : /movie/admin으로 보내준다.
			return "redirect:/movie/admin";

		} // 로그인 실패 시 -1을 리턴 받았을 때
		else {
			// 다시 로그인 페이지로 가줌.
			return "redirect:login";
		}

	}// 여기 블럭이 어디까지 해당하는 지 알고 싶으면 shift+alt+↑ 를 누르면 됨.

	// 로그아웃 시
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		/*
		 * getSession()의 매개변수 true : HttpSession이 존재하면 HttpSession을 반환하고 존재하지 않으면 새로이
		 * 세션을 생성
		 * 
		 * false : HttpSession이 존재하면 현재 HttpSession을 반환하고 존재하지 않으면 null을 반환
		 */
		HttpSession session = request.getSession(false);

		// session이 존재하면
		if (session != null) {
			// 세션삭제 - invalidate(): 세션을 없애고 세션에 속해있는 값들을 모두 삭제
			session.invalidate();
		}

		// url을 통해 index.jsp로 보내준다.
		return "redirect:/";
	}

	// idCheck 회원 가입할 때 비동기 방식으로 아이디 중복체크 해주기 위해 존재 -register.jsp와 연관 있음.
	@PostMapping("/idCheck")
	@ResponseBody
	public int idCheck(@RequestParam("chid") String id) {//chid파라미터 값을 id로 받아줌.
		int cnt = -1;
		try {
			cnt = dao.idCheck(id);//
		} catch (Exception e) {
			e.printStackTrace();
			cnt = -1;
			return cnt; // exception 발생 시 -1반환
		}
		return cnt;
	}

}
