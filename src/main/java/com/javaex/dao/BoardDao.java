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
	
	/* 전체 및 검색결과 select */
	public List<BoardVo> selectList(int startRnum, int endRnum, String kwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		map.put("kwd", kwd);
		
		return sqlSession.selectList("board.selectList", map);
	}
	
	/* 게시글 select */
	public BoardVo select(int no) {
		return sqlSession.selectOne("board.selectOne", no);
	}
	
	/* 게시글 insert */
	public int insert(BoardVo boardvo) {
		return sqlSession.insert("board.insert", boardvo);
	}
	
	/* 게시글 update */
	public int update(BoardVo boardvo) {
		return sqlSession.update("board.update", boardvo);
	}
	
	/* 조회수 update */
	public int updateHit(int no) {
		return sqlSession.update("board.updateHit", no);
	}
	
	/* 게시글 삭제 */
	public int delete(int no) {
		return sqlSession.delete("board.delete", no);
	}

	/* 전체 게시물 갯수 */
	public int totalCount(String kwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		
		return sqlSession.selectOne("board.totalCount", map);
	}

}
