package com.BoardWeb.board.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.BoardWeb.board.domain.Member;

public class SecurityUser extends User{

	
	private static final long serialVersionUID = 1L;
	private Member member; // 인증된 회원 정보를 HTML 화면에서 사용하기 위함

	public SecurityUser(Member member) { // JPA로 검색한 회원 정보로 부모 클래스의 면수들 초기화
		super(member.getId(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
		this.member = member;
	}
	public Member getMember() {
		return member;
	}

}
