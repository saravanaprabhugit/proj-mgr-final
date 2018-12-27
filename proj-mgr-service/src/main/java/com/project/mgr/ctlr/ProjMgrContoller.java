package com.project.mgr.ctlr;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.mgr.constants.ProjConstants;
import com.project.mgr.exception.PMException;
import com.project.mgr.request.GetParentTaskRequest;
import com.project.mgr.request.GetProjectRequest;
import com.project.mgr.request.GetTaskRequest;
import com.project.mgr.request.GetUserRequest;
import com.project.mgr.response.GetParentResponse;
import com.project.mgr.response.GetProjectResponse;
import com.project.mgr.response.GetTaskResponse;
import com.project.mgr.response.GetUserResponse;
import com.project.mgr.service.ProjectManagerService;

@RestController
public class ProjMgrContoller {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProjectManagerService projectManagerService;
	
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/viewTask", method = RequestMethod.POST, headers = "Accept=*/*", consumes = {
			"application/json; charset=UTF-8"}, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody GetTaskResponse viewTask(@RequestBody @Valid GetTaskRequest request) throws PMException {
		GetTaskResponse getTaskResponse = new GetTaskResponse();
		try {
			getTaskResponse = projectManagerService.viewTask(request.getTaskVO().getProjectId());
		} catch(PMException e) {
			logger.error("Error - ProjectManagerController viewTask : "+ e);
			throw new PMException(e.getErrorCode(), e.getErrorMessage(), 500);
		}
		return getTaskResponse;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getParentTask", method = RequestMethod.GET, headers = "Accept=*/*", produces = {"application/json; charset=UTF-8"})
	public @ResponseBody GetParentResponse getParentTask() throws PMException {
		GetParentResponse getParentTaskResponse = new GetParentResponse();
		try {
			getParentTaskResponse = projectManagerService.getParentTask();
		} catch(PMException e) {
			logger.error("Error - ProjectManagerController getParentTask : "+ e);
			throw new PMException(e.getErrorCode(), e.getErrorMessage(), 500);
		}
		return getParentTaskResponse;
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getProject", method = RequestMethod.GET, headers = "Accept=*/*", produces = {"application/json; charset=UTF-8"})
	public @ResponseBody GetProjectResponse getProject() throws PMException {
		GetProjectResponse getProjectResponse = new GetProjectResponse();
		try {
			getProjectResponse = projectManagerService.getProject();
		} catch(PMException e) {
			logger.error("Error - ProjectManagerController getProject : "+ e);
			throw new PMException(e.getErrorCode(), e.getErrorMessage(), 500);
		}
		return getProjectResponse;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getUser", method = RequestMethod.GET, headers = "Accept=*/*", produces = {"application/json; charset=UTF-8"})
	public @ResponseBody GetUserResponse getUser() throws PMException {
		GetUserResponse getUserResponse = new GetUserResponse();
		try {
			getUserResponse = projectManagerService.getUser();
		} catch(PMException e) {
			logger.error("Error - ProjectManagerController getUser : "+ e);
			throw new PMException(e.getErrorCode(), e.getErrorMessage(), 500);
		}
		return getUserResponse;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/updateTask", method = RequestMethod.POST, headers = "Accept=*/*", consumes = {
			"application/json; charset=UTF-8"}, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody GetTaskResponse updateTask(@RequestBody @Valid GetTaskRequest request) throws PMException {
		GetTaskResponse getTaskResponse = new GetTaskResponse();
		String status = ProjConstants.EMPTY;
		try {
			status = projectManagerService.updateTask(request);
			getTaskResponse.setStatus(status);
		} catch(PMException e) {
			logger.error("Error - ProjectManagerController updateTask : "+ e);
			throw new PMException(e.getErrorCode(), e.getErrorMessage(), 500);
		}
		return getTaskResponse;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/updateParentTask", method = RequestMethod.POST, headers = "Accept=*/*", consumes = {
			"application/json; charset=UTF-8"}, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody GetParentResponse updateParentTask(@RequestBody @Valid GetParentTaskRequest request) throws PMException {
		GetParentResponse getParentTaskResponse = new GetParentResponse();
		String status = ProjConstants.EMPTY;
		try {
			status = projectManagerService.updateParentTask(request);
			getParentTaskResponse.setStatus(status);
		} catch(PMException e) {
			logger.error("Error - ProjectManagerController updateParentTask : "+ e);
			throw new PMException(e.getErrorCode(), e.getErrorMessage(), 500);
		}
		return getParentTaskResponse;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/updateProject", method = RequestMethod.POST, headers = "Accept=*/*", consumes = {
			"application/json; charset=UTF-8"}, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody GetProjectResponse updateProject(@RequestBody @Valid GetProjectRequest request) throws PMException {
		GetProjectResponse getProjectResponse = new GetProjectResponse();
		String status = ProjConstants.EMPTY;
		try {
			status = projectManagerService.updateProject(request);
			getProjectResponse.setStatus(status);
		} catch(PMException e) {
			logger.error("Error - ProjectManagerController updateProject : "+ e);
			throw new PMException(e.getErrorCode(), e.getErrorMessage(), 500);
		}
		return getProjectResponse;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, headers = "Accept=*/*", consumes = {
			"application/json; charset=UTF-8"}, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody GetUserResponse updateUser(@RequestBody @Valid GetUserRequest request) throws PMException {
		GetUserResponse getUserResponse = new GetUserResponse();
		String status = ProjConstants.EMPTY;
		try {
			status = projectManagerService.updateUser(request);
			getUserResponse.setStatus(status);
		} catch(PMException e) {
			logger.error("Error - ProjectManagerController updateUser : "+ e);
			throw new PMException(e.getErrorCode(), e.getErrorMessage(), 500);
		}
		return getUserResponse;
	}
}