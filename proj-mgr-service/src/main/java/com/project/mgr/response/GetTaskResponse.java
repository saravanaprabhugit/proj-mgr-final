package com.project.mgr.response;

import java.util.List;

import com.project.mgr.vo.TaskVO;

public class GetTaskResponse {
	
	private List<TaskVO> taskVO;

	private String status;

	public List<TaskVO> getTaskVO() {
		return taskVO;
	}

	public void setTaskVO(List<TaskVO> taskVO) {
		this.taskVO = taskVO;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
