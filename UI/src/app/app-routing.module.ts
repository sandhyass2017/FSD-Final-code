import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AddProjectComponent } from './add-project/add-project.component';
import { AddTaskComponent }    from './add-task/add-task.component';
import { AddUserComponent }    from './add-user/add-user.component';
import { ViewTaskComponent }    from './view-task/view-task.component';

const routes: Routes = [
  { path: '', redirectTo: '/add-project', pathMatch: 'full' },
  { path: 'add-project', component: AddProjectComponent },
  { path: 'add-task', component: AddTaskComponent },
  { path: 'add-user', component: AddUserComponent },
  { path: 'view-task', component:  ViewTaskComponent }
];
	
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}