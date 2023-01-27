package com.h6.movie.mylist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.h6.movie.Movie;

@Component
public class MyListDAO {
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/movie";

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

	// mylist추가
	public void addMyList(String userid, int mid) {// 매개변수로 접속한 userid와 추가할 영화의 mid를 받아옴

		open();
		String sql = "insert into mylist values(?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setInt(2, mid);
			// dml문이므로 executeUpdate실행
			int result = pstmt.executeUpdate();
			System.out.println("mylist에 추가된 영화 개수 : " + result);

		} catch (SQLException e) {
			System.err.println(this.getClass().getName() + "의 addMyList()에서 에러 발생 : " + e.getMessage());

		} finally {
			close();
		}

	}

	// mylist삭제
	public void delMyList(String userid, int mid) {// 매개변수로 접속한 userid와 삭제할 영화의 mid를 받아옴
		open();

		String sql = "delete from mylist where userid = ? and mid = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setInt(2, mid);

			// dml문이므로 executeUpdate실행
			int result = pstmt.executeUpdate();
			System.out.println("mylist에서 삭제된 영화 개수 : " + result);
		} catch (SQLException e) {
			System.err.println(this.getClass().getName() + "의 deleteMyList()에서 에러 발생 : " + e.getMessage());
		} finally {
			close();
		}
	}

	// mylist목록 가져오기
	public MyList getAll(String userid) {// userid로 mylist들을 가져옴
		open();
		MyList mylist = new MyList();
		System.out.println(" get All userid : " + userid);
		String sql = "select mid,title from movie where mid in (select mid from mylist where userid = ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();

			// mylist의 userid를 매개변수로 받은 접속한 userid로 설정
			mylist.setUserid(userid);
			while (rs.next()) {
				// mylist테이블에 존재하는 행의 수동한 mylist의 treemap에 값을 계속 넣어줌.
				mylist.getMovies().put(rs.getInt("mid"), rs.getString("title"));
			}
			// mylist객체를 리턴
			return mylist;
		} catch (SQLException e) {
			System.err.println(this.getClass().getName() + "의 getAll()에서 에러 발생 : " + e.getMessage());
			return mylist;
		} finally {
			//pstmt와 conn을 닫아줌.
			
			close();
		}
	}

	// mylist에서 선택한 영화 가져오기
	public Movie getMovie(int mid) {
		open();
		//리턴해줄 Movie객체 생성
		Movie m = new Movie();

		//mylist테이블에는 userid,mid 칼럼만 있으므로 movie테이블에서 값을 가져온다.
		String sql = "select * from movie where mid = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);
		
			//mid는 primary key-> 고유한 값의 영화 하나만 갱신됨 혹은 존재하지 않거나
			ResultSet rs = pstmt.executeQuery();

			
			rs.next();
			//Movie객체에 값 설정
			m.setMid(rs.getInt("mid"));
			m.setTitle(rs.getString("title"));
			m.setDirector(rs.getString("director"));
			m.setActor(rs.getString("actor"));
			m.setCdate(rs.getDate("cdate"));
			m.setImg(rs.getString("img"));
			m.setRtime(rs.getInt("rtime"));
			m.setContent(rs.getString("content"));

		} catch (SQLException e) {
			System.err.println(this.getClass().getName() + "의 getMovie에서 에러발생 : " + e.getMessage());
		} finally {
			close();
		}
		//Movie 객체 리턴해줌.
		return m;
	}
}
