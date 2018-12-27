package com.project.manager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.project.mgr.entity.ParentTaskEnt;
import com.project.mgr.entity.ProjectEnt;
import com.project.mgr.entity.TaskEnt;
import com.project.mgr.entity.UserEnt;
import com.project.mgr.dao.ParentTaskRepository;
import com.project.mgr.dao.ProjectRepository;
import com.project.mgr.dao.TaskRepository;
import com.project.mgr.dao.UserRepository;
import com.project.mgr.request.GetParentTaskRequest;
import com.project.mgr.request.GetProjectRequest;
import com.project.mgr.request.GetTaskRequest;
import com.project.mgr.request.GetUserRequest;
import com.project.mgr.service.ProjectManagerService;
import com.project.mgr.service.ProjectManagerServiceImpl;

@RunWith(SpringRunner.class)
public class ProjectManagerServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String SUCCESS = "Success";
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ProjectRepository projectRepository;
	
	@MockBean
	private ParentTaskRepository parentTaskRepository;
	
	@MockBean
	private TaskRepository taskRepository;
	
	@Autowired
    private ProjectManagerService projectManagerService;
	
	@Configuration
	static class ProjectManagerServiceTestContextConfiguration {
		@Bean
		public ProjectManagerService projectManagerService() {
			return new ProjectManagerServiceImpl();
		}
	}

	@Before
    public void setUp() throws Exception {
		given(userRepository.findAll()).willReturn(getMockUser());
		given(projectRepository.findAll()).willReturn(getMockProject());
		given(parentTaskRepository.findAll()).willReturn(getMockParentTask());
		given(taskRepository.findByProjectId(1)).willReturn(getMockTask());
		given(projectRepository.findByUserId(1)).willReturn(getMockProjEnt());
	}
	
	@Test
	public void test_getUserService() throws Exception {
		assertNotNull(projectManagerService.getUser());
	}
	
	@Test
	public void test_getProjectService() throws Exception {
		assertNotNull(projectManagerService.getProject());
	}
	
	@Test
	public void test_getParentTaskService() throws Exception {
		assertNotNull(projectManagerService.getParentTask());
	}
	
	@Test
	public void test_getTaskService() throws Exception {
		assertNotNull(projectManagerService.viewTask(1));
	}
	
	@Test
	public void test_update_addUserService() throws Exception {
		assertNotNull(projectManagerService.updateUser(getMockUser_forAdd()));
		assertEquals("Success", projectManagerService.updateUser(getMockUser_forAdd()));
	}
	
	@Test
	public void test_update_addProjectService() throws Exception {
		assertNotNull(projectManagerService.updateProject(getMockProject_forAdd()));
		assertEquals(SUCCESS, projectManagerService.updateProject(getMockProject_forAdd()));
	}
	
	@Test
	public void test_update_addParentTaskService() throws Exception {
		assertNotNull(projectManagerService.updateParentTask(getMockParentTask_forAdd()));
		assertEquals(SUCCESS, projectManagerService.updateParentTask(getMockParentTask_forAdd()));
	}
	
	@Test
	public void test_update_addTaskService() throws Exception {
		assertNotNull(projectManagerService.updateTask(getMockTask_forAdd()));
		assertEquals(SUCCESS, projectManagerService.updateTask(getMockTask_forAdd()));
	}
	
	@Test
	public void test_update_deleteUserService() throws Exception {
		assertNotNull(projectManagerService.updateUser(getMockUser_forDelete()));
		assertEquals("Success", projectManagerService.updateUser(getMockUser_forDelete()));
	}
	
	@Test
	public void test_update_deleteProjectService() throws Exception {
		assertNotNull(projectManagerService.updateProject(getMockProject_forDelete()));
		assertEquals(SUCCESS, projectManagerService.updateProject(getMockProject_forDelete()));
	}
	
	@Test
	public void test_update_deleteParentTaskService() throws Exception {
		assertNotNull(projectManagerService.updateParentTask(getMockParentTask_forDelete()));
		assertEquals(SUCCESS, projectManagerService.updateParentTask(getMockParentTask_forDelete()));
	}
	
	@Test
	public void test_update_deleteTaskService() throws Exception {
		assertNotNull(projectManagerService.updateTask(getMockTask_forDelete()));
		assertEquals(SUCCESS, projectManagerService.updateTask(getMockTask_forDelete()));
	}
	
	private List<UserEnt> getMockUser() {
		Gson gson = new Gson();
		List<UserEnt> res = new ArrayList<UserEnt>();
		try {
			UserEnt ent = gson.fromJson(new FileReader("mock/getUserRes.json"), UserEnt.class);
			res.add(ent);
        } catch (Exception e) { 
            logger.error("Exception in ProjectManagerServiceTest getMockUser : " + e);
        }
		return res;
	}

	private List<ProjectEnt> getMockProject() {
		Gson gson = new Gson();
		List<ProjectEnt> res = new ArrayList<ProjectEnt>();
		try {
			ProjectEnt ent = gson.fromJson(new FileReader("mock/getProjectRes.json"), ProjectEnt.class);
			res.add(ent);
        } catch (Exception e) {
        	logger.error("Exception in ProjectManagerServiceTest getMockProject : " + e);
        }
		return res;
	}
	
	private List<ParentTaskEnt> getMockParentTask() {
		Gson gson = new Gson();
		List<ParentTaskEnt> res = new ArrayList<ParentTaskEnt>();
		try {
			ParentTaskEnt ent = gson.fromJson(new FileReader("mock/getParentTaskRes.json"), ParentTaskEnt.class);
			res.add(ent);
        } catch (Exception e) {
        	logger.error("Exception in ProjectManagerServiceTest getMockParentTask : " + e);
        }
		return res;
	}
	
	private List<TaskEnt> getMockTask() {
		Gson gson = new Gson();
		List<TaskEnt> res = new ArrayList<TaskEnt>();
		try {
			TaskEnt ent = gson.fromJson(new FileReader("mock/getTaskEntRes.json"), TaskEnt.class);
			res.add(ent);
        } catch (Exception e) {
        	logger.error("Exception in ProjectManagerServiceTest getMockTask : " + e);
        }
		return res;
	}
	
	private ProjectEnt getMockProjEnt() {
		Gson gson = new Gson();
		ProjectEnt res = new ProjectEnt();
		try {
			res = gson.fromJson(new FileReader("mock/projectEntRes.json"), ProjectEnt.class);
        } catch (Exception e) {
        	logger.error("Exception in ProjectManagerServiceTest getMockTask : " + e);
        }
		return res;
	}
	
	private GetUserRequest getMockUser_forAdd() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		GetUserRequest req = new GetUserRequest();
		req = gson.fromJson(new FileReader("mock/addUserReq.json"), GetUserRequest.class);
		return req;
	}
	
	private GetProjectRequest getMockProject_forAdd() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		GetProjectRequest req = new GetProjectRequest();
		req = gson.fromJson(new FileReader("mock/addProjectReq.json"), GetProjectRequest.class);
		return req;
	}
	
	private GetParentTaskRequest getMockParentTask_forAdd() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		GetParentTaskRequest req = new GetParentTaskRequest();
		req = gson.fromJson(new FileReader("mock/addParentTaskReq.json"), GetParentTaskRequest.class);
		return req;
	}
	
	private GetTaskRequest getMockTask_forAdd() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		GetTaskRequest req = new GetTaskRequest();
		req = gson.fromJson(new FileReader("mock/addTaskReq.json"), GetTaskRequest.class);
		return req;
	}
	
	private GetUserRequest getMockUser_forDelete() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		GetUserRequest req = new GetUserRequest();
		req = gson.fromJson(new FileReader("mock/deleteUserReq.json"), GetUserRequest.class);
		return req;
	}
	
	private GetProjectRequest getMockProject_forDelete() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		GetProjectRequest req = new GetProjectRequest();
		req = gson.fromJson(new FileReader("mock/deleteProjectReq.json"), GetProjectRequest.class);
		return req;
	}
	
	private GetParentTaskRequest getMockParentTask_forDelete() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		GetParentTaskRequest req = new GetParentTaskRequest();
		req = gson.fromJson(new FileReader("mock/deleteParentTaskReq.json"), GetParentTaskRequest.class);
		return req;
	}
	
	private GetTaskRequest getMockTask_forDelete() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		GetTaskRequest req = new GetTaskRequest();
		req = gson.fromJson(new FileReader("mock/deleteTaskReq.json"), GetTaskRequest.class);
		return req;
	}
	
}
