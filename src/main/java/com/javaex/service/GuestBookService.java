package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaex.dao.GuestBookDao;
import com.javaex.vo.GuestBookVo;

@Service
public class GuestBookService {

	@Autowired
	private GuestBookDao dao;
	
	/* 방명록 전체 목록 */
	public List<GuestBookVo> getList(){
		return dao.selectList();
	}
	
	/* 방명록 새 글 추가 */
	public int add(GuestBookVo vo) {
		return dao.insert(vo);
	}
	
	/* 방명록 삭제 */
	public int delete(GuestBookVo vo) {
		return dao.delete(vo);
	}

	@Transactional
	public GuestBookVo add2(GuestBookVo vo) {
		dao.insert(vo);
		return dao.selectByNo(vo);
	}

	public List<GuestBookVo> getScrollList(int startNo, int endNo) {
		return dao.selectListByRnum(startNo, endNo);
	}
}
