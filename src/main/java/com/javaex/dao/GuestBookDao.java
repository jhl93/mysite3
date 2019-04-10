package com.javaex.dao;

import java.util.List;

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
}
