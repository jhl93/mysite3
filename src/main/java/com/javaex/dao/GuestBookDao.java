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

	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = sqlSession.selectList("selectList");
		System.out.println(list.toString());

		return list;
	}

	public int insert(GuestBookVo vo) {
		int count = sqlSession.insert("insert", vo);

		return count;
	}

	public int delete(GuestBookVo vo) {
		int count = sqlSession.delete("delete", vo);

		return count;
	}
}
