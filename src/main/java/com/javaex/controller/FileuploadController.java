package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.FileuploadService;
import com.javaex.vo.FileVo;

@Controller
@RequestMapping("/file")
public class FileuploadController {

	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form() {
		return "fileupload/form";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, Model model) {
		System.out.println(file.getOriginalFilename());
		
		FileVo fileVo = fileuploadService.restore(file);
		model.addAttribute("fileVo", fileVo);
		
		return "fileupload/result";
	}
}
