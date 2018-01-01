package com.cognizant.pm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cognizant.pm.dao.ParentTask;
import com.cognizant.pm.dao.Project;
import com.cognizant.pm.dao.Task;
import com.cognizant.pm.dao.User;
import com.cognizant.pm.model.TaskObj;
import com.cognizant.pm.repository.ParentTaskRepository;
import com.cognizant.pm.repository.ProjectRepository;
import com.cognizant.pm.repository.TaskRepository;
import com.cognizant.pm.repository.UserRepository;

@Controller
@RequestMapping(path="/task")
public class TaskController {
	
	@Autowired 
	TaskRepository taskRepo;
	
	@Autowired
	private ParentTaskRepository repo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewTask (@RequestBody TaskObj task) {
		//System.out.println(task.isParentTask());
		if(task.isParentTask()){
			ParentTask pTask = new ParentTask();
			pTask.setParentTask(task.getTaskName());
			repo.save(pTask);
		}else{
			Task t = new Task();
			t.setParentId(task.getParentTaskId());
			t.setProjectId(task.getProjectId());
			t.setTask(task.getTaskName());
			t.setStartDate(task.getStartDate());
			t.setEndDate(task.getEndDate());
			t.setPriority(task.getPriority());
			t.setUserId(task.getUserId());	
			t.setStatus("STARTED");
			taskRepo.save(t);
		}
		
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody List<TaskObj> getAllTasks() {
		List<TaskObj> taskObjList = new ArrayList<>();
		List<Task> taskList =  (List<Task>) taskRepo.findAll();
		for(Task t: taskList){
			TaskObj obj = new TaskObj();
			obj.setTaskId(t.getTaskId());
			obj.setParentTaskId(t.getParentId());
			obj.setProjectId(t.getProjectId());
			obj.setTaskName(t.getTask());
			obj.setStartDate(t.getStartDate());
			obj.setEndDate(t.getEndDate());
			obj.setPriority(t.getPriority());
			obj.setStatus(t.getStatus());
			obj.setUserId(t.getUserId());
			if(t.getParentId() != null){
				ParentTask pTask = repo.findOne(t.getParentId());
				if(pTask != null){
					obj.setParentTaskName(pTask.getParentTask());
				}
			}
			if(t.getProjectId() != null){
				Project p = projectRepo.findOne(t.getProjectId());
				if(p != null){
					obj.setProjectName(p.getProject());
				}	
			}
			if(t.getUserId() != null){
				User u = userRepo.findOne(t.getUserId());
				if(u != null){
					obj.setUserName(u.getFirstName());
				}
			}
			
			taskObjList.add(obj);
			
		}
		return taskObjList;
	}
	
	@PutMapping(path="/update")
	public @ResponseBody Task updateTask(@RequestBody TaskObj task){
		
		Task t = taskRepo.findOne(task.getTaskId());
		t.setParentId(task.getParentTaskId());
		t.setProjectId(task.getProjectId());
		t.setTask(task.getTaskName());
		t.setStartDate(task.getStartDate());
		t.setEndDate(task.getEndDate());
		t.setPriority(task.getPriority());
        t.setStatus(task.getStatus());     
		return taskRepo.save(t);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteTask(@PathVariable("id") Integer id){
         taskRepo.delete(id);
	     return "return";
		
	}
	
	@RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
	public @ResponseBody Iterable<TaskObj> getTasksByProject(@PathVariable("id") Integer id){
 
		List<TaskObj> taskObjList = new ArrayList<>();
		List<Task> taskList = taskRepo.findAllByProjectId(id);
		for(Task t: taskList){
			TaskObj obj = new TaskObj();
			obj.setTaskId(t.getTaskId());
			obj.setParentTaskId(t.getParentId());
			obj.setProjectId(t.getProjectId());
			obj.setTaskName(t.getTask());
			obj.setStartDate(t.getStartDate());
			obj.setEndDate(t.getEndDate());
			obj.setPriority(t.getPriority());
			obj.setStatus(t.getStatus());
			obj.setUserId(t.getUserId());
			if(t.getParentId() != null){
				ParentTask pTask = repo.findOne(t.getParentId());
				if(pTask != null){
					obj.setParentTaskName(pTask.getParentTask());
				}
			}
			if(t.getProjectId() != null){
				Project p = projectRepo.findOne(t.getProjectId());
				if(p != null){
					obj.setProjectName(p.getProject());
				}	
			}
			if(t.getUserId() != null){
				User u = userRepo.findOne(t.getUserId());
				if(u != null){
					obj.setUserName(u.getFirstName());
				}
			}
			
			taskObjList.add(obj);
			
		}
		return taskObjList;
		
	}

}
