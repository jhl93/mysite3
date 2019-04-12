package com.javaex.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public String list(@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
			@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd, Model model) {
		System.out.println("list 요청");

		Map<String, Object> map = boardService.getList(crtPage, kwd);

		model.addAttribute("map", map);
		model.addAttribute("crtPage", crtPage);
		model.addAttribute("kwd", kwd);
		return "board/list";
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String read(@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
			@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd, @RequestParam("no") int no,
			Model model) {
		System.out.println("read 요청");

		BoardVo boardvo = boardService.read(no);
		model.addAttribute("boardvo", boardvo);
		model.addAttribute("crtPage", crtPage);
		model.addAttribute("kwd", kwd);
		return "board/read";
	}

	@RequestMapping(value = "/wform", method = RequestMethod.GET)
	public String wform(@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
			@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd, Model model) {
		System.out.println("wform 요청");

		model.addAttribute("crtPage", crtPage);
		model.addAttribute("kwd", kwd);

		return "board/writeform";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo boardvo, HttpSession session) {
		System.out.println("write 요청");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boardvo.setUserNo(authUser.getNo());

		int count = boardService.write(boardvo);
		System.out.println("성공 횟수: " + count);

		return "redirect:/board/list";
	}

	@RequestMapping(value = "/mform", method = RequestMethod.GET)
	public String mform(@RequestParam("no") int no, Model model,
			@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
			@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd) {
		System.out.println("mform 요청");

		BoardVo boardvo = boardService.read(no);
		model.addAttribute("boardvo", boardvo);
		model.addAttribute("crtPage", crtPage);
		model.addAttribute("kwd", kwd);
		return "board/modifyform";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo boardvo) {
		System.out.println("modify 요청");

		int count = boardService.modify(boardvo);
		System.out.println("성공 횟수: " + count);

		return "redirect:/board/read?no=" + boardvo.getNo();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("no") int no, HttpSession session,
			@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
			@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd, Model model) {
		System.out.println("delete 요청");

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		BoardVo boardvo = boardService.read(no);

		if (authUser != null && authUser.getNo() == boardvo.getUserNo()) {
			int count = boardService.delete(no);
			System.out.println("성공 횟수: " + count);
		}
		
		model.addAttribute("crtPage", crtPage);
		model.addAttribute("kwd", kwd);

		return "redirect:/board/list";
	}

}
