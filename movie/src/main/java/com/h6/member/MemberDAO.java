package com.h6.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class MemberDAO {

	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/movie";

	// 전역변수로 conn,pstmt 선언
	Connection conn = null;
	PreparedStatement pstmt;

	// DB연결 메서드
	public void open() {
		try {
			Class.forName(JDBC_DRIVER); // JDBC_DRIVER의 패키지의 클래스를 메모리에 올리려고 -> 호출되면서 메모리에 뜰때 자체적으로 뭔가를 하고 있다.
			//
			conn = DriverManager.getConnection(JDBC_URL, "h6", "1234");
		} catch (ClassNotFoundException e) {
			System.err.println("Connection open()에서 에러 발생 : " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Connection open()에서 에러 발생 : " + e.getMessage());
		}
	}

	// DB 종료
	public void close() {
		try {
//			pstmt.close();
//			System.out.println("pstmt닫힘? " + pstmt.isClosed());
//			System.out.println("conn닫힘?" + conn.isClosed());
			if (!pstmt.isClosed()) {
				pstmt.close();
			}
			if (!conn.isClosed()) {
				conn.close();
			}

		} catch (SQLException e) {
			System.err.println("Connection close()에서 에러 발생  : " + e.getMessage());
		}

	}

	// 아이디 중복 체크
	public int idCheck(String userid) throws Exception {
		open();
		String sql = "select * from member where userid = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userid);
		ResultSet rs = pstmt.executeQuery();

		// try()안에 pstmt와 conn을 넣어주지 않은 이유는 아이디를 입력하면서 계속 db와 연결되어야 하기 때문에
		try (rs) {
			if (rs.next()) {// 아이디가 중복되는 게 있으면 ResultSet에 값이 들어가기 때문에 rs.next()가 true 반환
				// 아이디가 중복되면 1 리턴
				return 1;
			} else {
				// 아이디가 중복이 아닐 때
				return 0;
			}
		} finally {
			// 작업이 다 끝나면 마지막에 pstmt와 conn을 닫아준다.
			close();
		}

	}

	// 아이디 & 비번 맞는지 -> 로그인 시 //수정 필요할 듯
	public int allCheck(String userid, String userpw) {
		open();

		// userid와 userpw가 해당하는게 있으면 하나의 결과값이 나온다.
		String sql = "select userid,admin from member where userid = ? and userpw = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userpw);
			ResultSet rs = pstmt.executeQuery();// ResultSet에 결과값 반환
			int admin;

			// 로그인 성공시 -> ResultSet에 결과가 나왔다는 것
			if (rs.next()) {
				admin = rs.getInt("admin");// admin의 칼럼값을 가져옴
				System.out.println(admin);
				rs.close();
				close();
				return admin;// 칼럼값 리턴(관리자 : 1, 일반회원 : 0)
			} else {// 로그인 실패 시
				rs.close();
				close();
				return -1;
			}
		} catch (SQLException e) {
			System.err.println(this.getClass().getName() + "의 allCheck()에서 에러 발생 " + e.getMessage());
		}
		return -1;

	}

	// 멤버 추가(일반 회원 추가) 관리자는 db에서 추가 할 것
	public void addMember(Member member) {
		open();
		String sql = "INSERT INTO member(userid, userpw, username, birth, tel) values(?,?,?,?,?)";
		PreparedStatement pstmt;
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getUserpw());
			pstmt.setString(3, member.getUsername());
			pstmt.setDate(4, member.getBirth());
			pstmt.setString(5, member.getTel());
			
			// insert된 행 개수 확인
			int result = pstmt.executeUpdate();
			System.out.println("add된 행 개수 : " + result);

		} catch (SQLException e) {

			System.err.println(this.getClass().getName() + "의 addMember에서 에러 발생 : " + e.getMessage());
		} finally {
			close();
		}

	}

}
