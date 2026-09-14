package com.ihor.taskmanager.service;
import com.ihor.taskmanager.exceptions.TaskNotFoundException;
import com.ihor.taskmanager.model.Task;
import com.ihor.taskmanager.model.TaskPriority;
import com.ihor.taskmanager.model.TaskStatus;
import com.ihor.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;


    @Test
    public void shouldFindById() {
        Task task = new Task(5L, "firsTask", "Java and SQL lerning", TaskStatus.TODO, TaskPriority.MEDIUM, LocalDateTime.now());
        when(taskRepository.findById(5L)).thenReturn(task);
        Task result = taskService.findById(5L);
        assertEquals(task, result);
    }

    @Test
    public void shouldThrowExceptionWhenIdNotExist() {
        when(taskRepository.findById(5L)).thenReturn(null);
        assertThrows(TaskNotFoundException.class,() ->taskService.findById(null));
    }

    @Test
    public void shouldCreateTask() {
        Task task = new Task(5L, "firsTask", "Java and SQL lerning", TaskStatus.TODO, TaskPriority.MEDIUM, LocalDateTime.now());
        when(taskRepository.save(task)).thenReturn(task);
        Task result = taskService.create(task);
        assertEquals(task,result);
    }

    @Test
    public void shouldThrowExceptionWhenRepositoryReturnsNull() {
        Task task = new Task(5L, "firsTask", "Java and SQL lerning", TaskStatus.TODO, TaskPriority.MEDIUM, LocalDateTime.now());
        when(taskRepository.save(task)).thenReturn(null);
        assertThrows(TaskNotFoundException.class, ()->taskService.create(task));
    }

    @Test
    public void shouldUpdateTask() {
        Task task = new Task(5L, "firsTask", "Java and SQL lerning", TaskStatus.TODO, TaskPriority.MEDIUM, LocalDateTime.now());

        when(taskRepository.update(task)).thenReturn(task);
        Task result = taskService.update(task);
        assertEquals(task,result);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenRequestedTaskIsNull() {
        assertThrows(IllegalArgumentException.class, ()->taskService.update(null));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenUpdatedTaskDoesntExistInDB() {
        Task task = new Task(5L, "firsTask", "Java and SQL lerning", TaskStatus.TODO, TaskPriority.MEDIUM, LocalDateTime.now());
        when(taskRepository.update(task)).thenReturn(null);
        assertThrows(TaskNotFoundException.class,()->taskService.update(task));
    }

    @Test
    public void shouldDeleteTask() {
        Task task = new Task(5L, "firsTask", "Java and SQL lerning", TaskStatus.TODO, TaskPriority.MEDIUM, LocalDateTime.now());
        when(taskRepository.findById(5L)).thenReturn(task);
        taskService.delete(5L);
        verify(taskRepository).delete(5L);
    }

    @Test
    public void shouldThrowExceptionAndDeleteNotCalledWhenDeletedTaskNotExist() {
        when(taskRepository.findById(5L)).thenReturn(null);
        assertThrows(TaskNotFoundException.class,() ->taskService.delete(5L));
        verify(taskRepository,never()).delete(5L);
    }

    @Test
    public void shouldFindAll() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(5L, "firsTask", "Java and SQL lerning", TaskStatus.TODO, TaskPriority.MEDIUM, LocalDateTime.now()));
        when(taskRepository.findAll()).thenReturn(taskList);
        assertEquals(taskList,taskService.findAll());
    }

    @Test
    public void shouldFindAllIfDBIsEmpty() {
        List<Task> taskList = new ArrayList<>();
        when(taskRepository.findAll()).thenReturn(taskList);
        assertEquals(taskList,taskService.findAll());
    }

}
