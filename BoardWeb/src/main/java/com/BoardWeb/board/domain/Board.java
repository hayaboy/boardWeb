package com.BoardWeb.board.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(exclude="member")  //순환 참조 문제 해결
@Entity
public class Board {
	
	@Id
	@GeneratedValue
	private Long seq;

	private String title;

	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createDate = new Date();
	
	@Column(updatable = false)
	private Long cnt = 0L;
	
	
	@ManyToOne   //다대일 관계에서 Board 엔티티는 양방향 매핑에서 다(N)쪽에 해당하면서 외래키(FK)를 가지고 있는 Board 테이블과 매핑, 따라서 Board 엔티티가 양방향 관계에서 연관관계 주인이 되며, MEMBER_ID 칼럼을 통해서 외래 키를 관리하도록 @JoinColumn 어노테이션 설정
	@JoinColumn(name="MEMBER_ID", nullable=false, updatable=false)  //외부 조인이 아닌 내부 조인으로 처리하여 성능 향상 시키키 위해 nullable 속성 false
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getBoardList().add(this);
	}
}
