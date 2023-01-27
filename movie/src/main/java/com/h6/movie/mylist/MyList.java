package com.h6.movie.mylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MyList {
	//로그인 한 사용자 id
	private String userid;
	//영화 제목
	private int mid;
	//movie 테이블의 mid, title 칼럼을 담을 Map
	private Map<Integer, String> movies;

	public MyList() {
		//MyList객체 생성되면서 treemap을 ㅎ생성
		movies = new TreeMap<>();
	}

	public void setMovies(TreeMap<Integer, String> movies) {
		this.movies = movies;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Map<Integer, String> getMovies() {
		return movies;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

}
