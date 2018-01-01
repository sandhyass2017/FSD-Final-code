import { Component, OnInit } from '@angular/core';
import { Router,ActivatedRoute,NavigationEnd } from '@angular/router';
import 'rxjs/add/operator/filter';

import { UserService } from '../user.service';
import { ProjectService } from '../project.service';
import { TaskService } from '../task.service';
import { User } from '../user';
import { Task } from '../task';
import { Project } from '../project';
import { ParentTask } from '../ParentTask';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit {



  users: User[];
  projects: Project[];
  parentTasks: ParentTask[];
  task: Object = {};

  disable: boolean = false;
  editTaskFlag : boolean = false;
  

  constructor(private userService: UserService, private projectService: ProjectService, private taskService: TaskService,
    router:Router, route:ActivatedRoute) { 
    
    if(this.taskService.task != null){
     // console.log(this.taskService.task);
      this.task = this.taskService.task;
      this.taskService.task = null;
      this.editTaskFlag = this.taskService.editTaskFlag;
      this.taskService.editTaskFlag = false;
    }
  }

  ngOnInit() {
      this.getUsers();
      this.getProjects();
      this.getParentTask();
  }

  getUsers(): void {
    this.userService.getUsers()
    .subscribe(users => this.users = users)
  }

  getProjects(): void{
   this.projectService.getProjects()
  .subscribe(projects => this.projects = projects)
  }

  addTask(task: Task): void {
   // console.log(task);
    this.taskService.addTask(task).subscribe();
  }

  editTask(task : Task): void {
    this.taskService.editTask(task).subscribe();
    this.editTaskFlag = false;
  }

  changeEvent(event: boolean): void{
    this.disable = event;
  }

  getParentTask(): void{
    this.taskService.getParentTask()
    .subscribe(parentTasks => this.parentTasks = parentTasks)
  }

}
