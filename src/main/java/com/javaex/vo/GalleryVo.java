package com.javaex.vo;

public class GalleryVo {

	private int no;
	private int userNo;
	private String comments;
	private String filePath;
	private String orgName;
	private String saveName;
	private long fileSize;

	public GalleryVo() {
	}

	public GalleryVo(int userNo, String comments, String filePath, String orgName, String saveName, long fileSize) {
		this.userNo = userNo;
		this.comments = comments;
		this.filePath = filePath;
		this.orgName = orgName;
		this.saveName = saveName;
		this.fileSize = fileSize;
	}

	public GalleryVo(int no, int userNo, String comments, String filePath, String orgName, String saveName,
			long fileSize) {
		this.no = no;
		this.userNo = userNo;
		this.comments = comments;
		this.filePath = filePath;
		this.orgName = orgName;
		this.saveName = saveName;
		this.fileSize = fileSize;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize2) {
		this.fileSize = fileSize2;
	}

	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", userNo=" + userNo + ", comments=" + comments + ", filePath=" + filePath
				+ ", orgName=" + orgName + ", saveName=" + saveName + ", fileSize=" + fileSize + "]";
	}

}
