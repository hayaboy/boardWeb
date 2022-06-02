package com.BoardWeb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BoardWeb.board.domain.Board;
import com.BoardWeb.board.domain.Search;
import com.BoardWeb.board.security.SecurityUser;
import com.BoardWeb.board.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/insertBoard")
	public String insertBoardView() {
		return "board/insertBoard";
	}

	// 로그인에 성공한 사용자는 연관관계 매핑에 의해서 새로운 게시글이 등록되기 위해서는 연관된 Member 엔티티를 Board 엔티티에 설정해야
	// 한다. 로그인 성공한 사용자 정보를 이용하기 위해서
	@PostMapping("/insertBoard") // 로그인 성공한 회원(Member) 객체를 가지고 있는 SecurityUser 객체를 매개 변수로 받으면 됨
									// @AuthenticationPrincipal 추가해야 인증정보를 가지고 있는 SecurityUser 객체가 할당
	public String insertBoard(Board board, @AuthenticationPrincipal SecurityUser principal) { // 그리고 SecurityUser 객체에서
																								// Member 엔티티를 꺼내서
																								// Board에 세팅
		board.setMember(principal.getMember());
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(Board board) {
		boardService.updateBoard(board);
		return "forward:getBoardList";
	}
	
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board) {
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}
	
	
			

	@RequestMapping("/getBoardList")
	public String getBoardList(Model model, Search search) {
		if(search.getSearchCondition() == null)
			search.setSearchCondition("TITLE");		
		if(search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		Page<Board> boardList = boardService.getBoardList(search);
		model.addAttribute("boardList", boardList);
		return "board/getBoardList";
	}

	@GetMapping("/getBoard")
	public String getBoard(Board board, Model model) {
		model.addAttribute("board", boardService.getBoard(board));
		return "board/getBoard";
	}

	

	
}
