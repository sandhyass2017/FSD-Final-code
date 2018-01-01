package com.cognizant.pm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.pm.dao.Project;
import com.cognizant.pm.dao.User;
import com.cognizant.pm.model.ProjectObj;
import com.cognizant.pm.repository.ProjectRepository;
import com.cognizant.pm.repository.TaskRepository;
import com.cognizant.pm.repository.UserRepository;

@RestController
@RequestMapping(path="/project")
public class ProjectController {

	@Autowired 
	private ProjectRepository projectRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	TaskRepository taskRepo;
	
	@PostMapping()
	public @ResponseBody ProjectObj addProject(@RequestBody ProjectObj p){
		Project project = new Project();
		project.setProject(p.getProjectName());
		project.setStartDate(p.getStartDate());
		project.setEndDate(p.getEndDate());
		project.setPriority(p.getPriority());
		project.setUserId(p.getUserId());
		
		projectRepository.save(project);
		
		p.setCompletedTaskNumber(0);
		p.setTaskNumber(0);
		
		return p;
	}
	
	@PutMapping
	public @ResponseBody ProjectObj updateProject(@RequestBody ProjectObj p){
		
		Project project = projectRepository.findOne(p.getProjectId());
		if(project!= null){
			project.setProject(p.getProjectName());
			project.setStartDate(p.getStartDate());
			project.setEndDate(p.getEndDate());
			project.setPriority(p.getPriority());
			project.setUserId(p.getUserId());
			projectRepository.save(project);
			return p;
		}else{
			return null;
		}
	}
	
	@GetMapping(path="/all")
	public @ResponseBody List<ProjectObj> getAllProject() {
		
		 Iterable<Project> projectList =  projectRepository.findAll();
		 List<ProjectObj> projectResponseList = new ArrayList<>();
		 for(Project project: projectList){
			 ProjectObj p = new ProjectObj();
			 p.setProjectId(project.getProjectId());
			 p.setProjectName(project.getProject());
			 p.setStartDate(project.getStartDate());
			 p.setEndDate(project.getEndDate());
			 p.setPriority(project.getPriority());
			 p.setTaskNumber(taskRepo.findAllByProjectId(project.getProjectId()).size());
			 p.setCompletedTaskNumber(taskRepo.findAllByProjectIdAndStatus(project.getProjectId(), "COMPLETED").size());
			 p.setUserId(project.getUserId());
			 if(project.getUserId() != null){
				 User u = userRepository.findOne(project.getUserId());
				 if( u != null){
					 p.setManager(u.getFirstName());
				 }
			 }
			 
			 projectResponseList.add(p);
		 }
		return projectResponseList;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteProject(@PathVariable("id") Integer id){
		projectRepository.delete(id);
	     return "deleted";
		
	}
	
}
