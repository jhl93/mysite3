package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;
import com.javaex.vo.RboardVo;

@Repository
public class RboardDao {

	@Autowired
	private SqlSession sqlSession;

	/* 전체 게시글 갯수 */
	public int totalCount(String kwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		
		return sqlSession.selectOne("rboard.totalCount", map);
	}

	/* 전체 및 검색결과 select */
	public List<BoardVo> selectList(int startRnum, int endRnum, String kwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		map.put("kwd", kwd);
		
		return sqlSession.selectList("rboard.selectList", map);
	}

	/* 게시글 select */
	public RboardVo select(int no) {
		return sqlSession.selectOne("rboard.selectOne", no);
	}
	
	/* 게시글 insert */
	public int insert(RboardVo rboardvo) {
		return sqlSession.insert("rboard.insert", rboardvo);
	}
	
	/* 게시글 update */
	public int update(RboardVo rboardvo) {
		return sqlSession.update("rboard.update", rboardvo);
	}
	
	/* 조회수 update */
	public int updateHit(int no) {
		return sqlSession.update("rboard.updateHit", no);
	}

	/* 답글 insert */
	public int insertReply(RboardVo rboardvo) {
		return sqlSession.insert("rboard.insertReply", rboardvo);
	}

	/* 답글 작성 시 같은 groupNo에서 orderNo update */
	public int updateOrderNo(RboardVo rboardvo) {
		return sqlSession.update("rboard.updateOrderNo", rboardvo);
	}

	/* 게시글 state update */
	public int updateState(int no) {
		return sqlSession.update("rboard.updateState", no);
	}
	
	
}
