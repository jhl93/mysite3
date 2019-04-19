package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestBookService;
import com.javaex.vo.GuestBookVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/gb")
public class GuestBookController {

	@Autowired
	private GuestBookService guestbookService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		System.out.println("list 요청");

		List<GuestBookVo> list = guestbookService.getList();
		model.addAttribute("list", list);
		return "guestbook/addlist";
	}

	@RequestMapping(value = "/dform", method = RequestMethod.GET)
	public String dform() {
		System.out.println("dform 요청");

		return "guestbook/deleteform";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute GuestBookVo vo) {
		System.out.println("delete 요청");

		int count = guestbookService.delete(vo);
		System.out.println("성공 횟수: " + count);
		
		return "redirect:/gb/list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute GuestBookVo vo) {
		System.out.println("add 요청");

		int count = guestbookService.add(vo);
		System.out.println("성공 횟수: " + count);

		return "redirect:/gb/list";
	}
	
	@RequestMapping(value = "/ajaxlist", method = RequestMethod.GET)
	public String ajaxList() {
		
		return "guestbook/ajax_list";
	}
}
