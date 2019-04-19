package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.interceptor.Auth;
import com.javaex.interceptor.AuthUser;
import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/loginform", method = RequestMethod.GET)
	public String loginform() {
		System.out.println("loginform 요청");

		return "user/loginform";
	}

	/*
	 * @RequestMapping(value = "/login", method = RequestMethod.POST) public String
	 * login(@RequestParam("email") String email, @RequestParam("password") String
	 * password, HttpSession session) { System.out.println("login 요청");
	 * System.out.println("email: " + email + " / password: " + password);
	 * 
	 * UserVo authUser = userService.login(email, password); //
	 * System.out.println(authUser.toString());
	 * 
	 * if (authUser == null) { return "redirect:/user/loginform?result=fail"; } else
	 * { session.setAttribute("authUser", authUser); return "redirect:/main"; } }
	 */
	
	/*
	 * @RequestMapping(value = "/logout", method = RequestMethod.GET) public String
	 * logout(HttpSession session) { session.removeAttribute("authUser");
	 * session.invalidate(); // 세션을 없애고 속해있는 값들까지 없앰
	 * 
	 * return "redirect:/main"; }
	 */

	@RequestMapping(value = "/joinform", method = RequestMethod.GET)
	public String joinform() {
		System.out.println("joinform 요청");

		return "user/joinform";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo uservo) {
		System.out.println("join 요청");
		System.out.println(uservo.toString());

		int count = userService.join(uservo);
		System.out.println("성공 횟수: " + count);

		return "user/joinsuccess";
	}

	@Auth
	@RequestMapping(value = "/modifyform", method = RequestMethod.GET)
	public String modifyform(@AuthUser UserVo authUser, Model model) {
		System.out.println("modifyform 요청");

		authUser = userService.getInfo(authUser);

		model.addAttribute("uservo", authUser);
		return "user/modifyform";
	}

	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute UserVo uservo, @AuthUser UserVo authUser) {
		System.out.println("modify 요청");

		System.out.println(uservo.toString());
		int count = userService.modify(uservo);
		System.out.println("성공 횟수: " + count);

		authUser.setName(uservo.getName());
		return "redirect:/main";
	}
	
	@ResponseBody
	@RequestMapping(value = "/emailcheck", method = RequestMethod.POST)
	public boolean emailCheck(@RequestParam("email") String email) {
		System.out.println(email);
		boolean result = userService.emailCheck(email);
		return result;
	}
}
