package com.h6.movie;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Movie {
//	private static int serialNum = 0;

	// 영화 아이디
	private int mid;
	// 제목
	private String title;
	// 감독
	private String director;
	// 출연 배우
	private String actor;
	// 개봉일
	private Date cdate;
	// 이미지경로
	private String img;
	// 상영시간
	private int rtime;
	// 줄거리
	private String content;

	public Movie() {

	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getRtime() {
		return rtime;
	}

	public void setRtime(int rtime) {
		this.rtime = rtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
