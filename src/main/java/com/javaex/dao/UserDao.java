package com.javaex.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	/* 회원정보 저장 */
	public int insert(UserVo uservo) {
		return sqlSession.insert("user.insert", uservo);
	}
	
	/* 회원정보 가져오기(email, password) */
	public UserVo select(String email, String password) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("email", email);
		userMap.put("password", password);
		return sqlSession.selectOne("user.select", userMap);
	}
	
	public UserVo select(UserVo uservo) {
		return sqlSession.selectOne("user.selectUser", uservo);
	}
	
	public int update(UserVo uservo) {
		return sqlSession.update("user.update", uservo);
	}
}
