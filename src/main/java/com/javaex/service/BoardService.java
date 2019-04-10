package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao dao;
	
	public List<BoardVo> getList(){
		return dao.selectList();
	}
	
	public BoardVo read(int no) {
		return dao.select(no);
	}
	
	public int write(BoardVo boardvo) {
		return dao.insert(boardvo);
	}
	
	public int modify(BoardVo boardvo) {
		return dao.update(boardvo);
	}
	
	public int delete(int no) {
		return dao.delete(no);
	}
}
