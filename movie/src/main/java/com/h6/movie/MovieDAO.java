package com.h6.movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MovieDAO {
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/movie";

	// 전역변수로 conn,pstmt 선언
	Connection conn = null;
	PreparedStatement pstmt;

	// DB연결
	public void open() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "h6", "1234");
		} catch (ClassNotFoundException e) {
			System.err.println("Connection open()에서 에러 발생 : " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Connection open()에서 에러 발생 : " + e.getMessage());
		}
	}

	// DB 종료
	public void close() {
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Connection close()에서 에러 발생  : " + e.getMessage());
		}
	}

	// 영화 추가
	public void addMovie(Movie m) {
		open();
		String sql = "insert into movie(title,director,actor,cdate,img,rtime,content) values(?,?,?,?,?,?,?)";

		try {
			// mid는 db에서 auto_increment이기 때문에 따로 지정 x
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getTitle());
			pstmt.setString(2, m.getDirector());
			pstmt.setString(3, m.getActor());
			pstmt.setDate(4, m.getCdate());
			pstmt.setString(5, m.getImg());
			pstmt.setInt(6, m.getRtime());
			pstmt.setString(7, m.getContent());

			int result = pstmt.executeUpdate();
			System.out.println("add된 행 개수 : " + result);// add된 행개수 확인

		} catch (SQLException e) {
			System.err.println(this.getClass().getName() + "의 addMovie()에서 에러 발생!! : " + e.getMessage());

		} finally {
			close();
		}

	}

	// 영화 삭제
	public void delMovie(int mid) {
		open();
		String sql = "delete from movie where mid = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);

			// executeUpdate()는 select를 제외한 dml을 쓸 때 사용
			int result = pstmt.executeUpdate();// delete가 되면 1반환 할것 -> 1은 dml이 일어난 행 개수
			// dml : select, insert, delete, update
			System.out.println("delete된 행의 개수 : " + result);

		} catch (SQLException e) {
			System.err.println(this.getClass().getName() + "의 delMovie()에서 에러 발생 : " + e.getMessage());
		} finally {
			close();
		}
	}

	// 전체 영화 가져오기
	public List<Movie> getAll() {
		open();
		// 전체 영화들을 가져올 moveList
		List<Movie> movieList = new ArrayList<>();

		String sql = "select * from movie";

		try {
			pstmt = conn.prepareStatement(sql);
			// select 할 때는 executeQuery()를 사용
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				// Movie객체를 while문 진행하는 동안 생성 계속 값 설정
				Movie m = new Movie();
				m.setMid(rs.getInt("mid"));
				m.setTitle(rs.getString("title"));
				m.setDirector(rs.getString("director"));
				m.setActor(rs.getString("actor"));
				m.setCdate(rs.getDate("cdate"));
				m.setImg(rs.getString("img"));
				m.setRtime(rs.getInt("rtime"));
				m.setContent(rs.getString("content"));

				// moveList.에 계속 넣어줌
				movieList.add(m);
			}
			// 영화들이 다 들어간 후 movieList리턴
			return movieList;
		} catch (SQLException e) {
			System.err.println(this.getClass().getName() + "의 getAll()에서 에러 발생 : " + e.getMessage());
			return movieList;
		} finally {
			close();
		}
	}

	// 선택 영화 가져오기
	public Movie getMovie(int mid) {
		open();
		Movie m = new Movie();

		//mid는 db에서 primary key 이므로 고유한 값 -> 선택된 행의 결관는 1행 또는 0(mid에 해당하는 영화 없는 경우)
		String sql = "select * from movie where mid = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);
			//select문이므로 executeQuery()실행
			ResultSet rs = pstmt.executeQuery();

			rs.next();//rs.next()실행
			
			m.setMid(rs.getInt("mid"));
			m.setTitle(rs.getString("title"));
			m.setDirector(rs.getString("director"));
			m.setActor(rs.getString("actor"));
			m.setCdate(rs.getDate("cdate"));
			m.setImg(rs.getString("img"));
			m.setRtime(rs.getInt("rtime"));
			m.setContent(rs.getString("content"));

		}

		catch (SQLException e) {
			System.err.println(this.getClass().getName() + "의 getMovie()에서 에러 발생 : " + e.getMessage());
			return m;
		} finally {
			close();
		}
		return m;

	}
}
