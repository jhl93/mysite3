package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.util.S3Util;

@Controller
@RequestMapping("/aws")
public class AwsController {
	
	@Autowired
	private S3Util s3Util;
	
	private String bucketName = "com.javaex.jonghyun.upload";
	
	//테스트용
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String init() {
		//버킷생성
		//s3Util.createBucket(bucketName);
		
		//버킷리스트
		//System.out.println(s3Util.getBucketlist());
		
		//폴더만들기
		s3Util.createFolder(bucketName, "img_test");
		
		return "aws/form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form() {
		
		return "aws/form";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, Model model) {
		System.out.println("aws 파일업로드");
		System.out.println(file.getOriginalFilename());
		
		s3Util.fileUpload(bucketName, file, file.getOriginalFilename());
		
		model.addAttribute("url", s3Util.getFileURL(bucketName, file.getOriginalFilename()));
		
		return "aws/result";
	}
}
