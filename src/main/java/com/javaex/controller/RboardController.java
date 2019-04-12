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

import com.javaex.service.RboardService;
import com.javaex.vo.RboardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/rboard")
public class RboardController {

	@Autowired
	private RboardService rboardService;

	/* 전체 및 검색결과 출력 */
	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public String list(@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
					   @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd, 
					   Model model) {
		System.out.println("list 요청");

		Map<String, Object> map = rboardService.getList(crtPage, kwd);

		model.addAttribute("map", map);
		model.addAttribute("crtPage", crtPage);
		model.addAttribute("kwd", kwd);
		return "rboard/list";
	}

	/* 게시글 읽기 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String read(@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
					   @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd, 
					   @RequestParam("no") int no,
					   Model model) {
		System.out.println("read 요청");

		RboardVo rboardvo = rboardService.read(no);
		if("delete".equals(rboardvo.getState())) {
			return "redirect:/rboard/list";
		} else {
			model.addAttribute("rboardvo", rboardvo);
			model.addAttribute("crtPage", crtPage);
			model.addAttribute("kwd", kwd);
			return "rboard/read";
		}
	}

	/* 글 작성 form */
	@RequestMapping(value = "/wform", method = RequestMethod.GET)
	public String wform(@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
						@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						@ModelAttribute RboardVo rboardvo, 
						Model model, HttpSession session) {
		System.out.println("wform 요청");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser != null) {
			model.addAttribute("rboardvo", rboardvo);
			model.addAttribute("crtPage", crtPage);
			model.addAttribute("kwd", kwd);
	
			return "rboard/writeform";
		} else {
			return "redirect:/rboard/list";
		}
	}

	/* 게시글 및 답글 작성 */
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute RboardVo rboardvo, HttpSession session) {
		System.out.println("write 요청");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		rboardvo.setUserNo(authUser.getNo());

		if (rboardvo.isReply()) { // 일반글 아닌 답글일 경우 true
			int count = rboardService.reply(rboardvo);
			System.out.println("답글 성공 횟수: " + count);
		} else {
			int count = rboardService.write(rboardvo);
			System.out.println("성공 횟수: " + count);
		}
		return "redirect:/rboard/list";
	}

	/* 게시글 수정 form */
	@RequestMapping(value = "/mform", method = RequestMethod.GET)
	public String mform(@RequestParam("no") int no, Model model,
						@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
						@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						HttpSession session) {
		System.out.println("mform 요청");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		RboardVo rboardvo = rboardService.read(no);
		
		if (authUser != null && authUser.getNo() == rboardvo.getUserNo()) {
			model.addAttribute("rboardvo", rboardvo);
			model.addAttribute("crtPage", crtPage);
			model.addAttribute("kwd", kwd);
			return "rboard/modifyform";
		} else {
			return "redirect:/rboard/list";
		}
	}

	/* 게시글 수정 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute RboardVo rboardvo) {
		System.out.println("modify 요청");

		int count = rboardService.modify(rboardvo);
		System.out.println("성공 횟수: " + count);

		return "redirect:/rboard/read?no=" + rboardvo.getNo();
	}
	
	/* 게시글 삭제 (state = 'delete') */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("no") int no, HttpSession session,
						 @RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
						 @RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
						 Model model) {
		System.out.println("delete 요청");

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		RboardVo rboardvo = rboardService.read(no);

		if (authUser != null && authUser.getNo() == rboardvo.getUserNo()) {
			int count = rboardService.delete(no);
			System.out.println("성공 횟수: " + count);
		}
		
		model.addAttribute("crtPage", crtPage);
		model.addAttribute("kwd", kwd);

		return "redirect:/rboard/list";
	}
}
