/**
 * ProjectManagerService.java
 *
 * Modification History
 *
 * Date        Version   Developer      Description
 * ---------   -------   ------------   --------------------------------------
 * 11/23/2018   1.0	 	 Cognizant		Initial version
 */
package com.project.mgr.service;

import com.project.mgr.exception.PMException;
import com.project.mgr.request.GetParentTaskRequest;
import com.project.mgr.request.GetProjectRequest;
import com.project.mgr.request.GetTaskRequest;
import com.project.mgr.request.GetUserRequest;
import com.project.mgr.response.GetParentResponse;
import com.project.mgr.response.GetProjectResponse;
import com.project.mgr.response.GetTaskResponse;
import com.project.mgr.response.GetUserResponse;

public interface ProjectManagerService {
	
	public GetTaskResponse viewTask(int projectId) throws PMException;
	
	public GetParentResponse getParentTask() throws PMException;
	
	public GetProjectResponse getProject() throws PMException;

	public GetUserResponse getUser() throws PMException;
	
	public String updateTask(GetTaskRequest request) throws PMException;
	
	public String updateParentTask(GetParentTaskRequest request) throws PMException;
	
	public String updateProject(GetProjectRequest request) throws PMException;
	
	public String updateUser(GetUserRequest request) throws PMException;
	
}
