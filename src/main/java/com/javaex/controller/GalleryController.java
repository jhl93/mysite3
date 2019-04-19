package com.javaex.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.javaex.interceptor.Auth;
import com.javaex.interceptor.AuthUser;
import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

	@Autowired
	private GalleryService galleryService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		System.out.println("list 요청");
		return "gallery/list";
	}

	@ResponseBody
	@RequestMapping(value = "/getlist", method = RequestMethod.POST)
	public List<GalleryVo> getlist() {
		System.out.println("getlist 요청");
		List<GalleryVo> galleryList = galleryService.getList();
		return galleryList;
	}

	@ResponseBody
	@RequestMapping(value = "/getOne", method = RequestMethod.POST)
	public GalleryVo getOne(@RequestBody GalleryVo galleryVo) {
		System.out.println("getone 요청");
		galleryVo = galleryService.getOne(galleryVo);

		return galleryVo;
	}

	@Auth
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public GalleryVo upload(MultipartHttpServletRequest request, @ModelAttribute GalleryVo vo, @AuthUser UserVo authUser) {
		System.out.println("upload 요청");
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next());

		vo.setUserNo(authUser.getNo());

		GalleryVo galleryVo = galleryService.restore(vo, file);

		return galleryVo;
	}

	@Auth
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public int delete(@ModelAttribute GalleryVo galleryVo, @AuthUser UserVo authUser) {
		System.out.println("delete 요청");
		
		galleryVo.setUserNo(authUser.getNo());
			
		int count = galleryService.delete(galleryVo);
		System.out.println("삭제 횟수: " + count);
		if (count == 0) {
			return 0;
		} else {
			return galleryVo.getNo();
		}
	}
}
