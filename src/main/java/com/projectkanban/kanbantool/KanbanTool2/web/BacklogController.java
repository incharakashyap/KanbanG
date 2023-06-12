package com.projectkanban.kanbantool.KanbanTool2.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectkanban.kanbantool.KanbanTool2.domain.ProjectTask;
import com.projectkanban.kanbantool.KanbanTool2.services.MapValidationErrorService;
import com.projectkanban.kanbantool.KanbanTool2.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	//Adding a new Project Task
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, Principal principal){
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		
		if(errorMap != null) return errorMap;
		
		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());
				
				return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
		
	}
	
	//Getting Backlog with Project Task by ID
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal){
		
		return projectTaskService.findBacklogById(backlog_id, principal.getName());
	}
	
	
	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}
	
	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updatedProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);	
		if(errorMap != null) return errorMap;
		
		ProjectTask updatedTask= projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id, principal.getName());
		
		return new ResponseEntity<ProjectTask>(updatedTask,HttpStatus.OK);	
	}

	@DeleteMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
		projectTaskService.deleteByProjectSequence(backlog_id, pt_id, principal.getName());
		
		return new ResponseEntity<String>("Project Task '"+pt_id+"' was deleted successfully", HttpStatus.OK);
	}
	
	
	
	
	
}
