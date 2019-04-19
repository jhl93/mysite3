package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.interceptor.Auth;
import com.javaex.service.GuestBookService;
import com.javaex.vo.GuestBookVo;

@Controller
@RequestMapping(value = "/api/gb")
public class ApiGuestBookController {

	@Autowired
	private GuestBookService guestbookService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<GuestBookVo> list() {
		System.out.println("ajax-list");
		List<GuestBookVo> guestbookList = guestbookService.getList();
		System.out.println(guestbookList.toString());
		return guestbookList;
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public GuestBookVo add(@RequestBody GuestBookVo guestbookVo) {
		System.out.println("add 요청");
		System.out.println(guestbookVo.toString());

		guestbookVo = guestbookService.add2(guestbookVo);
		return guestbookVo;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public boolean delete(@ModelAttribute GuestBookVo guestbookVo) {
		System.out.println("delete 요청");
		System.out.println(guestbookVo.toString());

		int count = guestbookService.delete(guestbookVo);
		boolean result = true;
		if(count != 1) {
			result = false;
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/scrollList", method = RequestMethod.POST)
	public List<GuestBookVo> scrollList(@RequestParam("startNo") int startNo,
										@RequestParam("endNo") int endNo){
		System.out.println("scrollList 요청");
		System.out.println(startNo + " / " + endNo);
		
		List<GuestBookVo> list = guestbookService.getScrollList(startNo, endNo);
		System.out.println(list.toString());
		return list;
	}
}
