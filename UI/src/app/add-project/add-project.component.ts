import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Project } from '../project';
import { UserService } from '../user.service';
import { User } from '../user';
import { ProjectService } from '../project.service';


@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit {

  addProjectForm: FormGroup;
  editProjectFlag: boolean = false;

  users: User[];
  projects: Project[];
  project: Object = {};

  enableDate: boolean = false;

  constructor(private userService: UserService,private projectService: ProjectService) { 
  }

  ngOnInit() {
  	this.getUsers();
  	this.getProjects();
  }

  getUsers(): void {
    this.userService.getUsers()
    .subscribe(users => this.users = users)
  }

  getProjects(): void{
  	 this.projectService.getProjects()
    .subscribe(projects => this.projects = projects)
  }

  addProject(project: Project): void{
    console.log(project);
  	this.projectService.addProject(project)
      .subscribe(project => {
        this.projects.push(project);
      });
  }

  deleteProject(project: Project): void{
    console.log(" delete ");
    this.projects = this.projects.filter(p => p !== project);
    this.projectService.deleteProject(project.projectId).subscribe();
  }

  editProject(project: Project): void{
    this.editProjectFlag = true;
    this.project = project;
  }

  onSubmit(project: Project): void{
    console.log(this.editProjectFlag);
    if(this.editProjectFlag){
      this.editProjectFlag = false;
      this.projects = this.projects.filter(p => p !== project);
      this.projectService.updateProject(project).subscribe(
        project => {this.projects.push(project);})
    }else{
      this.addProject(project);
    }
  }

   changeEvent(event: boolean): void{
    this.enableDate = event;
  }

  sortByStartDate(): void{
     this.projects.sort((a, b) => {
        if (a.startDate < b.startDate) return -1;
        else if (a.startDate > b.startDate) return 1;
        else return 0;
      });
  }

  sortByEndDate(): void{
     this.projects.sort((a, b) => {
        if (a.endDate < b.endDate) return -1;
        else if (a.endDate > b.endDate) return 1;
        else return 0;
      });
  }

  sortByPriority(): void{
     this.projects.sort((a, b) => {
        if (a.priority < b.priority) return -1;
        else if (a.priority > b.priority) return 1;
        else return 0;
      });
  }

  sortByCompleted(): void{
     /*this.projects.sort((a, b) => {
        if (a.status < b.status) return -1;
        else if (a.status > b.status) return 1;
        else return 0;
      });*/
  }

}
