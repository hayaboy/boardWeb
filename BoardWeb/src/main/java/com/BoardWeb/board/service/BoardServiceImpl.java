package com.BoardWeb.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.BoardWeb.board.domain.Board;
import com.BoardWeb.board.domain.QBoard;
import com.BoardWeb.board.domain.Search;
import com.BoardWeb.board.persistence.BoardRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardRepository boardRepo;
	
	
	@Override
	public void insertBoard(Board board) {
		boardRepo.save(board);
		//엔티티를 영속성 컨텍스트에 저장하기 위해서는 원래 JPA의 persist()메소드를 사용했었다. 
		//하지만 CurdRepository 인터페이스를 사용할 때는 save() 메소드를 이용해서 등록한다.
		
	}

	@Override
	public void updateBoard(Board board) {
		Board findBoard = boardRepo.findById(board.getSeq()).get();
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());		
		boardRepo.save(findBoard);
	}

	@Override
	public void deleteBoard(Board board) {
		boardRepo.deleteById(board.getSeq());
		
	}

	@Override
	public Board getBoard(Board board) {
		
		return boardRepo.findById(board.getSeq()).get();
	}

	@Override
	public Page<Board> getBoardList(Search search) {
		BooleanBuilder builder= new BooleanBuilder();
		
		QBoard qboard = QBoard.board;
		
		if(search.getSearchCondition().equals("TITLE")) {
		      builder.and(qboard.title.like("%" + search.getSearchKeyword() + "%"));
		} else if(search.getSearchCondition().equals("CONTENT")) {
		      builder.and(qboard.content.like("%" + search.getSearchKeyword() + "%"));
		}	
		
		
		
		
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "seq");
		return boardRepo.findAll(builder, pageable);
	}

}
