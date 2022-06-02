package com.BoardWeb.board.persistence;

import org.springframework.data.repository.CrudRepository;

import com.BoardWeb.board.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String>{

}
