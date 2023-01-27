package com.h6.member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Member {
	// 아이디
	private String userid;
	// 패스워드
	private String userpw;
	// 이름
	private String username;
	// 생년월일
	private Date birth;
	// 전화번호
	private String tel;

	// db에서 default값으로 admin = 0 으로 설정(일반 회원 0, 관리자 1)
	private int admin;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpw() {
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

}
