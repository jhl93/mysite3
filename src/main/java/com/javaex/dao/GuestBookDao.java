package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	@Autowired
	private SqlSession sqlSession;

	public List<GuestBookVo> selectList() {
		return sqlSession.selectList("guestbook.selectList");
	}

	public int insert(GuestBookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}

	public int delete(GuestBookVo vo) {
		return sqlSession.delete("guestbook.delete", vo);
	}

	public List<GuestBookVo> selectListByRnum(int startNo, int endNo) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNo", startNo);
		map.put("endNo", endNo);
		
		return sqlSession.selectList("guestbook.selectListByRnum", map);
	}

	public GuestBookVo selectByNo(GuestBookVo vo) {
		return sqlSession.selectOne("guestbook.selectByNo", vo);
	}

}
