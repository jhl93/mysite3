package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao dao;

	/* 회원가입 */
	public int join(UserVo uservo) {
		return dao.insert(uservo);
	}

	/* 로그인 */
	public UserVo login(String email, String password) {
		return dao.select(email, password);
	}

	/* 회원정보 출력을 위해 한 명의 정보 select */
	public UserVo getInfo(UserVo uservo) {
		return dao.select(uservo);
	}

	/* 회원정보 수정 */
	public int modify(UserVo uservo) {
		return dao.update(uservo);
	}

	/* 이메일 체크 */
	public boolean emailCheck(String email) {
		UserVo vo = dao.select(email);

		if (vo == null) { // 데이터가 없으면 true -> 가입 가능
			return true;
		} else {
			return false;
		}
	}
}
