package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> selectList(){
		return sqlSession.selectList("board.selectList");
	}
	
	public BoardVo select(int no) {
		return sqlSession.selectOne("board.selectOne", no);
	}
	
	public int insert(BoardVo boardvo) {
		return sqlSession.insert("board.insert", boardvo);
	}
	
	public int update(BoardVo boardvo) {
		return sqlSession.update("board.update", boardvo);
	}
	
	public int delete(int no) {
		return sqlSession.delete("board.delete", no);
	}
}
