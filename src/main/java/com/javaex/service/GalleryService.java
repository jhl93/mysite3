package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;

@Service
public class GalleryService {

	@Autowired
	private GalleryDao dao;

	public GalleryVo restore(GalleryVo vo, MultipartFile file) {

		String saveDir = "/home/bituser/upload";
		//String saveDir = "D:\\bitStudy\\upload";

		// 오리지날 파일명
		String orgName = file.getOriginalFilename();
		System.out.println("orgName: " + orgName);

		// 확장자
		String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		System.out.println("exName: " + exName);

		// 저장할 파일명
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString();
		System.out.println("saveName: " + saveName);

		// 파일패스
		String filePath = saveDir + "/" + saveName;
		System.out.println("filePath: " + filePath);

		// 파일사이즈
		long fileSize = file.getSize();
		System.out.println("fileSize: " + fileSize);

		vo.setFilePath(filePath);
		vo.setOrgName(orgName);
		vo.setSaveName(saveName);
		vo.setFileSize(fileSize);

		// 서버 파일 복사
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(saveDir + "/" + saveName);
			BufferedOutputStream bout = new BufferedOutputStream(out);

			bout.write(fileData);

			if (bout != null) {
				bout.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(vo.toString());
		int count = dao.insertFile(vo);
		System.out.println("성공 횟수: " + count);
		System.out.println(vo.toString());
		return dao.selectByNo(vo);
	}

	public List<GalleryVo> getList() {
		return dao.selectList();
	}

	public GalleryVo getOne(GalleryVo galleryVo) {
		return dao.selectByNo(galleryVo);
	}

	public int delete(GalleryVo galleryVo) {
		return dao.delete(galleryVo);
	}
}
