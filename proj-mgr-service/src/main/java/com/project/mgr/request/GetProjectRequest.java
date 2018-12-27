package com.project.mgr.request;

import com.project.mgr.vo.ProjectVO;

public class GetProjectRequest {
	
	private ProjectVO projectVO;

	private String action;

	public ProjectVO getProjectVO() {
		return projectVO;
	}

	public void setProjectVO(ProjectVO projectVO) {
		this.projectVO = projectVO;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
