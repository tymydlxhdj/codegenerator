package com.mqfdy.code.designer.models;

public class ModelMessage {

	public String id;
	
	public String modelType;  //模型类型
	
	public String modelName;   //模型名称
	
	public String sharePerson;   //共享人
	
	public String desc;         //描述
	
	public String  createTime ;   //创建时间
	
	public String  file ;        //文件
	
	public String  fileId ;        //文件编号
	
	public String  ssxm ;        //所属项目

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getSharePerson() {
		return sharePerson;
	}

	public void setSharePerson(String sharePerson) {
		this.sharePerson = sharePerson;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getSsxm() {
		return ssxm;
	}

	public void setSsxm(String ssxm) {
		this.ssxm = ssxm;
	}
	
	
	
}
