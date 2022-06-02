package com.BoardWeb.board.domain;

import lombok.Data;


//검색 전용 클래스로서 검색 관련 정보만 저장


@Data  //롬복에서 제공하는 어노테이션, @Getter, @Setter, @ToString을 모두 포함
public class Search {
	private String searchCondition;
	private String searchKeyword;
}
