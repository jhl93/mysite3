package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {

	@Autowired
	private SqlSession sqlSession;

	public int insertFile(GalleryVo vo) {
		return sqlSession.insert("gallery.insertFile", vo);
	}

	public GalleryVo selectByNo(GalleryVo vo) {
		return sqlSession.selectOne("gallery.selectByNo", vo);
	}

	public List<GalleryVo> selectList() {
		return sqlSession.selectList("gallery.selectList");
	}

	public int delete(GalleryVo galleryVo) {
		return sqlSession.delete("gallery.delete", galleryVo);
	}

}
