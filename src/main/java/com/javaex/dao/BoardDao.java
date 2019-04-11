package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> selectList(int startRnum, int endRnum, String kwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		map.put("kwd", kwd);
		
		return sqlSession.selectList("board.selectList", map);
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
	
	public int updateHit(int no) {
		return sqlSession.update("board.updateHit", no);
	}
	
	public int delete(int no) {
		return sqlSession.delete("board.delete", no);
	}

	public int totalCount(String kwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		
		return sqlSession.selectOne("board.totalCount", map);
	}

}
