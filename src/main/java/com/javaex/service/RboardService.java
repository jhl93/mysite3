package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaex.dao.RboardDao;
import com.javaex.vo.BoardVo;
import com.javaex.vo.RboardVo;

@Service
public class RboardService {

	@Autowired
	private RboardDao dao;

	/* 전체 및 검색결과 (페이징) */
	public Map<String, Object> getList(int crtPage, String kwd) {
		// 페이지당 글 갯수
		int listCnt = 10;

		// 현재 페이지
		crtPage = (crtPage > 0) ? crtPage : (crtPage = 1);

		// 시작글 번호
		int startRnum = (crtPage - 1) * listCnt; // 1page -> 0

		// 끝글 번호
		int endRnum = startRnum + listCnt; // 1page -> 10

		List<BoardVo> list = dao.selectList(startRnum, endRnum, kwd);

		// 페이징 계산
		// 전체 글 갯수
		int totalCount = dao.totalCount(kwd);

		// 페이지당 버튼 갯수
		int pageBtnCount = 5;

		// 마지막 버튼 번호
		// 1 -> 1~5
		// 2 -> 1~5
		// 3 -> 1~5

		// 7 -> 6~10
		// 10 -> 6~10
		int endPageBtnNo = (int) Math.ceil(crtPage / (double) pageBtnCount) * pageBtnCount;

		// 시작 버튼 번호
		int startPageBtnNo = endPageBtnNo - (pageBtnCount - 1);

		// 다음 화살표 유무
		boolean next = false;
		if (endPageBtnNo * listCnt < totalCount) { // 10 * 10 < 107
			next = true;
		} else {
			endPageBtnNo = (int) Math.ceil(totalCount / (double) listCnt);
		}

		// 이전 화살표 유무
		boolean prev = false;
		if (startPageBtnNo != 1) {
			prev = true;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("startPageBtnNo", startPageBtnNo);
		map.put("endPageBtnNo", endPageBtnNo);
		map.put("next", next);
		map.put("prev", prev);
		map.put("kwd", kwd);

		System.out.println(map.toString());
		return map;
	}

	/* 게시글 작성 */
	public int write(RboardVo rboardvo) {
		return dao.insert(rboardvo);
	}

	/* 게시글 읽기 */
	@Transactional
	public RboardVo read(int no) {
		dao.updateHit(no);
		return dao.select(no);
	}

	/* 게시글 수정 */
	public int modify(RboardVo rboardvo) {
		return dao.update(rboardvo);
	}

	/* 답글 작성 */
	@Transactional
	public int reply(RboardVo rboardvo) {
		
		int count = dao.updateOrderNo(rboardvo);
		System.out.println("성공 횟수: " + count);
		
		return dao.insertReply(rboardvo);
	}

	/* 게시글 삭제 */
	public int delete(int no) {
		return dao.updateState(no);
	}

}
