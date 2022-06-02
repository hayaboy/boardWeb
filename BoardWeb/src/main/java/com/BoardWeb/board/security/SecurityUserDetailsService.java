package com.BoardWeb.board.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BoardWeb.board.domain.Member;
import com.BoardWeb.board.persistence.MemberRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepo;

	
	@Override  // MemberRepository를 이용하여 조회한 회원 정보를 SecurityUser 객체로 감싸서 리턴
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optional = memberRepo.findById(username);
		if (!optional.isPresent()) {
			throw new UsernameNotFoundException(username + " 사용자 없음");
		}else {
			Member member = optional.get();
			return new SecurityUser(member);
		}
		

	}

}
