package com.project.mgr.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mgr.constants.ProjConstants;
import com.project.mgr.dao.ParentTaskRepository;
import com.project.mgr.dao.ProjectRepository;
import com.project.mgr.dao.TaskRepository;
import com.project.mgr.dao.UserRepository;
import com.project.mgr.entity.ParentTaskEnt;
import com.project.mgr.entity.ProjectEnt;
import com.project.mgr.entity.TaskEnt;
import com.project.mgr.entity.UserEnt;
import com.project.mgr.exception.PMException;
import com.project.mgr.request.GetParentTaskRequest;
import com.project.mgr.request.GetProjectRequest;
import com.project.mgr.request.GetTaskRequest;
import com.project.mgr.request.GetUserRequest;
import com.project.mgr.response.GetParentResponse;
import com.project.mgr.response.GetProjectResponse;
import com.project.mgr.response.GetTaskResponse;
import com.project.mgr.response.GetUserResponse;
import com.project.mgr.util.CommonUtil;
import com.project.mgr.vo.ParentTaskVO;
import com.project.mgr.vo.ProjectVO;
import com.project.mgr.vo.TaskVO;
import com.project.mgr.vo.UserVO;

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService, ProjConstants {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ParentTaskRepository parentTaskRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public GetTaskResponse viewTask(int projectId) throws PMException {
		// TODO Auto-generated method stub
		GetTaskResponse getTaskResponse = new GetTaskResponse();
		List<TaskVO> taskVOList = null;
		try {
			List<TaskEnt> taskEntList = getTaskByProjectId(projectId);
			if(null != taskEntList && !taskEntList.isEmpty()) {
	        	taskVOList = new ArrayList<>(); 
	        	
	        	for(TaskEnt taskEnt : taskEntList) {
		        	TaskVO task = new TaskVO();
		        	task.setTaskId(taskEnt.getTaskId());
		        	task.setTask(taskEnt.getTask());
		        	
		        	task.setParentId(null != taskEnt.getParentTaskEnt() ? taskEnt.getParentTaskEnt().getParentId() : 0);
		        	task.setParentTaskName(null != taskEnt.getParentTaskEnt() ? taskEnt.getParentTaskEnt().getParentTask() : "");
		        	
		        	task.setProjectId(taskEnt.getProjectEnt().getProjectId());
		        	task.setProjectName(taskEnt.getProjectEnt().getProject());
		        	
		        	task.setUserId(taskEnt.getProjectEnt().getUserEnt().getUserId());
		        	task.setUserFName(taskEnt.getProjectEnt().getUserEnt().getFname());
		        	task.setUserLName(taskEnt.getProjectEnt().getUserEnt().getLname());
		        	
		        	task.setPriority(taskEnt.getPriority());
		        	task.setStartDate(CommonUtil.dateToString(taskEnt.getStartDate()));
		        	task.setEndDate(CommonUtil.dateToString(taskEnt.getEndDate()));
		        	task.setStatus(taskEnt.getStatus());
		        	
		        	taskVOList.add(task);
	        	}
	        }
	        getTaskResponse.setTaskVO(taskVOList);
	        getTaskResponse.setStatus("Success");
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl viewTask : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return getTaskResponse;
	}

	@Override
	public GetParentResponse getParentTask() throws PMException {
		// TODO Auto-generated method stub
		GetParentResponse getParentTaskResponse = new GetParentResponse();
		List<ParentTaskVO> parentTaskVOList = null;
		List<ParentTaskEnt> parentTaskEntList = null;
		try {
			parentTaskEntList = parentTaskRepository.findAll();
			if(null != parentTaskEntList && !parentTaskEntList.isEmpty()) {
	        	parentTaskVOList = new ArrayList<>();
	        	
	        	for(ParentTaskEnt parentTaskEnt : parentTaskEntList) {
	        		ParentTaskVO parentTask = new ParentTaskVO();
	        		parentTask.setParentId(parentTaskEnt.getParentId());
	        		parentTask.setParentTaskName(parentTaskEnt.getParentTask());
	        		parentTask.setProjectId(null != parentTaskEnt.getProjectEnt() ? parentTaskEnt.getProjectEnt().getProjectId() : 0);
		        	
	        		parentTaskVOList.add(parentTask);
	        	}
	        }
	        getParentTaskResponse.setParentTaskVO(parentTaskVOList);
	        getParentTaskResponse.setStatus("Success");
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl getParentTask : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return getParentTaskResponse;
	}
	
	@Override
	public GetProjectResponse getProject() throws PMException {
		// TODO Auto-generated method stub
		GetProjectResponse getProjectResponse = new GetProjectResponse();
		List<ProjectVO> projectVOList = null;
		int completedTaskCount = 0;
		try {
			List<ProjectEnt> projectEntList = projectRepository.findAll();
			if(null != projectEntList && !projectEntList.isEmpty()) {
	        	projectVOList = new ArrayList<>();
	        	
	        	for(ProjectEnt projectEnt : projectEntList) {
	        		ProjectVO project = new ProjectVO();
	        		project.setProjectId(projectEnt.getProjectId());
	        		project.setProject(projectEnt.getProject());
	        		project.setPriority(projectEnt.getPriority());
	        		project.setStartDate(CommonUtil.dateToString(projectEnt.getStartDate()));
	        		project.setEndDate(CommonUtil.dateToString(projectEnt.getEndDate()));
	        		project.setEmpId(null != projectEnt.getUserEnt() ? projectEnt.getUserEnt().getUserId() : 0);
		        	
	        		List<TaskEnt> taskEntList = getTaskByProjectId(projectEnt.getProjectId());
	        		if(null != taskEntList && !taskEntList.isEmpty()) {
	        			for(TaskEnt taskEnt : taskEntList) {
	        				if(null != taskEnt.getStatus() && STATUS_COMPLETED.equalsIgnoreCase(taskEnt.getStatus())) {
	        					completedTaskCount ++;
	        				}
	        			}
	        			project.setNoOfTask(taskEntList.size());
	        			project.setNoOfCompletedTask(completedTaskCount);
	        			completedTaskCount = 0;
	        		}
		        	projectVOList.add(project);
	        	}
	        }
			getProjectResponse.setProjectVO(projectVOList);
			getProjectResponse.setStatus("Success");
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl getProject : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return getProjectResponse;
	}

	@Override
	public GetUserResponse getUser() throws PMException {
		// TODO Auto-generated method stub
		GetUserResponse getUserResponse = new GetUserResponse();
		List<UserVO> userVOList = null;
		try {
			List<UserEnt> userEntList = userRepository.findAll();
			if(null != userEntList && !userEntList.isEmpty()) {
	        	userVOList = new ArrayList<>();
	        	
	        	for(UserEnt userEnt : userEntList) {
		        	UserVO user = new UserVO();
		        	user.setUserId(userEnt.getUserId());
		        	user.setFname(userEnt.getFname());
		        	user.setLname(userEnt.getLname());
		        	user.setEmpId(userEnt.getEmpId());
		        	
		        	userVOList.add(user);
	        	}
	        }
			getUserResponse.setUserVO(userVOList);
			getUserResponse.setStatus("Success");
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl getUser : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return getUserResponse;
	}

	@Override
	public String updateTask(GetTaskRequest request) throws PMException {
		// TODO Auto-generated method stub
		TaskEnt taskEnt = new TaskEnt();
		String status = EMPTY;
		try {
			ProjectEnt projectEnt = projectRepository.findByProjectId(request.getTaskVO().getProjectId());
			ParentTaskEnt parentTaskEnt = parentTaskRepository.findByParentTaskId(request.getTaskVO().getParentId());
			
			taskEnt.setTaskId(request.getTaskVO().getTaskId());
			taskEnt.setTask(request.getTaskVO().getTask());
			taskEnt.setStartDate(CommonUtil.stringToDate(request.getTaskVO().getStartDate()));
			taskEnt.setEndDate(CommonUtil.stringToDate(request.getTaskVO().getEndDate()));
			taskEnt.setPriority(request.getTaskVO().getPriority());
			taskEnt.setProjectEnt(projectEnt);
			taskEnt.setParentTaskEnt(parentTaskEnt);
			taskEnt.setStatus(request.getTaskVO().getStatus());
			taskRepository.save(taskEnt);
			status = "Success";
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl updateTask : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return status;
	}

	@Override
	public String updateParentTask(GetParentTaskRequest request) throws PMException {
		// TODO Auto-generated method stub
		String status = EMPTY;
		ParentTaskEnt parentTaskEnt = new ParentTaskEnt();
		try {
			if(ACTION_DELETE.equalsIgnoreCase(request.getAction())) {
				parentTaskEnt.setParentId(request.getParentTaskVO().getParentId());
				if(deleteTask(request.getParentTaskVO().getParentId())) {
					parentTaskRepository.delete(parentTaskEnt);
				} else {
					logger.error("updateParentTask - Task deletion failed");
					status = USER_DELETE_FAILED_MESSAGE;
				}
			} else {
				ProjectEnt projectEnt = projectRepository.findByProjectId(request.getParentTaskVO().getProjectId());
				
				parentTaskEnt.setParentId(request.getParentTaskVO().getParentId());
				parentTaskEnt.setParentTask(request.getParentTaskVO().getParentTaskName());
				parentTaskEnt.setProjectEnt(projectEnt);
				parentTaskRepository.save(parentTaskEnt);
			}
			
			status = "Success";
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl updateParentTask : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return status;
	}
	
	@Override
	public String updateProject(GetProjectRequest request) throws PMException {
		// TODO Auto-generated method stub
		String status = EMPTY;
		ProjectEnt projectEnt = new ProjectEnt();
		try {
			if(ACTION_DELETE.equalsIgnoreCase(request.getAction())) {
				projectEnt.setProjectId(request.getProjectVO().getProjectId());
				if(deleteTask(request.getProjectVO().getProjectId())) {
					if(deleteParentTask(request.getProjectVO().getProjectId())) {
						projectRepository.delete(projectEnt);
					} else {
						logger.error("updateProject - Parent Task deletion failed");
						status = USER_DELETE_FAILED_MESSAGE;
					}
				} else {
					logger.error("updateProject - Task deletion failed");
					status = USER_DELETE_FAILED_MESSAGE;
				}
			} else {
				UserEnt userEnt = userRepository.findByUserId(request.getProjectVO().getEmpId());
				
				projectEnt.setProjectId(request.getProjectVO().getProjectId());
				projectEnt.setProject(request.getProjectVO().getProject());
				projectEnt.setPriority(request.getProjectVO().getPriority());
				projectEnt.setStartDate(CommonUtil.stringToDate(request.getProjectVO().getStartDate()));
				projectEnt.setEndDate(CommonUtil.stringToDate(request.getProjectVO().getEndDate()));
				projectEnt.setUserEnt(userEnt);
				projectRepository.save(projectEnt);
			}
			status = "Success";
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl updateProject : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return status;
	}

	@Override
	public String updateUser(GetUserRequest request) throws PMException {
		// TODO Auto-generated method stub
		UserEnt userEnt = new UserEnt();
		String status = EMPTY;
		try {
      userEnt.setUserId(request.getUserVO().getUserId());
			if(ACTION_DELETE.equalsIgnoreCase(request.getAction())) {
				status = deleteUser(request, userEnt);
			} else {
				userEnt.setFname(request.getUserVO().getFname());
				userEnt.setLname(request.getUserVO().getLname());
				userEnt.setEmpId(request.getUserVO().getEmpId());
				userRepository.save(userEnt);
				status = "Success";
			}
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl updateUser : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return status;
	}
	
	private List<TaskEnt> getTaskByProjectId(int projectId) throws PMException {
		List<TaskEnt> taskEntList = null;
		try {
			taskEntList = taskRepository.findByProjectId(projectId);
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl getTaskByProjectId : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return taskEntList;
	}
	
	private boolean deleteTask(int projectId) throws PMException {
		boolean flag = false;
		try {
			taskRepository.deleteByProjectId(projectId);
			flag = true;
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl deleteTask : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return flag;
	}
	
	private boolean deleteParentTask(int projectId) throws PMException {
		boolean flag = false;
		try {
			parentTaskRepository.deleteByProjectId(projectId);
			flag = true;
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl deleteParentTask : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return flag;
	}
	
	private boolean deleteProject(int userId) throws PMException {
		boolean flag = false;
		try {
			projectRepository.deleteByUserId(userId);
			flag = true;
		} catch(Exception e) {
			logger.error("Error - ProjectManagerServiceImpl deleteProject : " + e);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		}
		return flag;
	}
	
	private String deleteUser(GetUserRequest request, UserEnt userEnt) throws PMException {
		String status = "Success";
		ProjectEnt project = projectRepository.findByUserId(request.getUserVO().getUserId());
		if(null != project) {
			userEnt.setUserId(request.getUserVO().getUserId());
			if(deleteTask(project.getProjectId())) {
				if(deleteParentTask(project.getProjectId())) {
					if(deleteProject(request.getUserVO().getUserId())) {
						userRepository.delete(userEnt);
					} else {
						logger.error("deleteUser - Project deletion failed");
						status = USER_DELETE_FAILED_MESSAGE;
					}
				} else {
					logger.error("deleteUser - Parent Task deletion failed");
					status = USER_DELETE_FAILED_MESSAGE;
				}
			} else {
				logger.error("deleteUser - Task deletion failed");
				status = USER_DELETE_FAILED_MESSAGE;
			}
		}
		return status;
	}
}
