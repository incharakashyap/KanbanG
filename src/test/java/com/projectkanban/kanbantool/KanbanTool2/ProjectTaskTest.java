package com.projectkanban.kanbantool.KanbanTool2;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.projectkanban.kanbantool.KanbanTool2.domain.Backlog;
import com.projectkanban.kanbantool.KanbanTool2.domain.Project;
import com.projectkanban.kanbantool.KanbanTool2.domain.ProjectTask;
import com.projectkanban.kanbantool.KanbanTool2.repositories.ProjectTaskRepository;
import com.projectkanban.kanbantool.KanbanTool2.services.ProjectService;
import com.projectkanban.kanbantool.KanbanTool2.services.ProjectTaskService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ProjectTaskTest {
    @Mock
    private ProjectTaskRepository projectTaskRepo;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectTaskService projectTaskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	@Test
	public void testProjectId() {
		Long id = 007L;
		String backlogId = "BLG1";
		String ptId = "PT1";
		String userName = "john_doe";
		Backlog backlog = new Backlog();
		backlog.setId(id);
		ProjectTask projectTask = new ProjectTask();
		projectTask.setBacklog(backlog);
		assertEquals(id, projectTask.getBacklog().getId());
	}

	@Test
	public void testProjectId_null() {
		String backlogId = "BLG1";
		String ptId = "PT1";
		String userName = "john_doe";
		Backlog backlog = new Backlog();
		backlog.setId(null);
		ProjectTask projectTask = new ProjectTask();
		projectTask.setBacklog(backlog);
		assertEquals(null, projectTask.getBacklog().getId());
	}

	@Test
	public void testFindBacklogById() {
		String backlogId = "BLG1";
		String ptId = "PT1";
		String userName = "john_doe";
		when(projectService.findProjectByIdentifier(eq(backlogId), eq(userName))).thenReturn(new Project());
		when(projectTaskRepo.findByProjectSequence(eq(ptId))).thenReturn(new ProjectTask());

		ProjectTask projectTask = new ProjectTask();
		projectTask.setProjectIdentifier(ptId);
		String testId = projectTask.getProjectIdentifier();
		assertEquals("PT1", testId);
	}

	@Test
	public void testBacklogId() {
		String backlogId = "BLG1";
		String ptId = "PT1";
		String userName = "john_doe";
		when(projectService.findProjectByIdentifier(eq(backlogId), eq(userName))).thenReturn(new Project());
		when(projectTaskRepo.findByProjectSequence(eq(ptId))).thenReturn(new ProjectTask());
		Backlog backlog = new Backlog();
		backlog.setProjectIdentifier(backlogId);
		String testId = backlog.getProjectIdentifier();
		assertEquals("BLG1", testId);
	}

	@Test
	public void deleteTaskTest() {
		String backlogId = "BLG1";
		String ptId = "PT1";
		String userName = "john_doe";
		when(projectService.findProjectByIdentifier(eq(backlogId), eq(userName))).thenReturn(new Project());
		when(projectTaskRepo.findByProjectSequence(eq(ptId))).thenReturn(new ProjectTask());
		ProjectTask projectTask = new ProjectTask();
		projectTask.setProjectIdentifier(ptId);
		assertEquals("PT1", projectTask.getProjectIdentifier());
	}

	@Test
	public void deleteTaskTestNullCheck() {
		String backlogId = "BLG1";
		String userName = "john_doe";
		when(projectService.findProjectByIdentifier(eq(backlogId), eq(userName))).thenReturn(new Project());
		ProjectTask projectTask = new ProjectTask();
		assertEquals(null, projectTask.getProjectIdentifier());
	}

	@Test
	public void backlogNullIdCheck() {
		String ptId = "PT1";
		String userName = "john_doe";
		when(projectTaskRepo.findByProjectSequence(eq(ptId))).thenReturn(new ProjectTask());
		Backlog backlog = new Backlog();
		assertEquals(null, backlog.getId());
	}
}


