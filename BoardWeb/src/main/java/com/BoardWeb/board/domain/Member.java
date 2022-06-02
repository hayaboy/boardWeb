package com.BoardWeb.board.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="boardList")   //순환 참조 문제 해결
@Entity
public class Member {
	@Id 
	@Column(name="MEMBER_ID")
	private String id;

	private String password;

	private String name;

	@Enumerated(EnumType.STRING)
	private Role role;

	private boolean enabled;
	
	//Member가 양방향 매핑에서 연관관계의 주인이 아님을 설정하기 이해 mappedBy 속성 추가
	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER) //일대다 관계 매핑, fetch 속성은 회원 정보를 조회할 때 연관관계에 있는 게시판 정보도 같이 조회할 것이지 결정할 때 사용, 기본값은 LAZY, EAGER로 설정했기 때문에, 회원정보를 가져올 때 회원이 등록한 게시 글 목록도 같이 조회
	private List<Board> boardList = new ArrayList<Board>();
	
	
	
	
	

}
