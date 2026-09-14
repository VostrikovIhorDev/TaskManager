package com.ihor.taskmanager.repository;

import com.ihor.taskmanager.model.Task;
import com.ihor.taskmanager.model.TaskPriority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskRepositoryTest {

    private final TaskRepository taskRepository = new TaskRepository();
    private ArrayList<Long> savedTasksID = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        if (savedTasksID.size() > 0) {
            for (Long savedTaskID : savedTasksID) {
                taskRepository.delete(savedTaskID);
            }

        }
    }

    @Test
    void shouldSaveTask() {
        Task task = new Task("firsTask", "Java and SQL lerning", TaskPriority.MEDIUM);
        Task savedTask = taskRepository.save(task);
        savedTasksID.add(savedTask.getId());
        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getDescription(), savedTask.getDescription());
        assertEquals(task.getPriority(), savedTask.getPriority());
        assertNotNull(savedTask.getId());
        assertNotNull(savedTask.getCreatedAt());
    }

    @Test
    void shouldFindTaskByID() {
        Task task = new Task("firsTask", "Java and SQL lerning", TaskPriority.MEDIUM);
        Task savedTask = taskRepository.save(task);
        savedTasksID.add(savedTask.getId());
        Task foundTask = taskRepository.findById(savedTask.getId());
        assertNotNull(foundTask);
        assertNotNull(foundTask.getCreatedAt());
        assertEquals(task.getStatus(), foundTask.getStatus());
        assertEquals(task.getTitle(), foundTask.getTitle());
        assertEquals(task.getDescription(), foundTask.getDescription());
        assertEquals(task.getPriority(), foundTask.getPriority());
    }

    @Test
    void shouldFindAllTasks() {
        Task task1 = new Task("firsTask", "Java and SQL lerning", TaskPriority.MEDIUM);
        Task savedTask1 = taskRepository.save(task1);
        savedTasksID.add(savedTask1.getId());
        Task task2 = new Task("firsTask2", "Java and SQL lerning2", TaskPriority.HIGH);
        Task savedTask2 = taskRepository.save(task2);
        savedTasksID.add(savedTask2.getId());
        List<Task> taskList = taskRepository.findAll();
        assertNotNull(taskList);
        assertTrue(taskList.stream().anyMatch(currentTask -> currentTask.getId().equals(savedTask1.getId())));
        assertTrue(taskList.stream().anyMatch(currentTask -> currentTask.getId().equals(savedTask2.getId())));
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task("firsTask", "Java and SQL lerning", TaskPriority.MEDIUM);
        Task savedTask = taskRepository.save(task);
        savedTask.setTitle("secondTask");
        savedTask.setDescription("Spring learning");
        savedTask.setPriority(TaskPriority.HIGH);
        Task updatedTask = taskRepository.update(savedTask);
        savedTasksID.add(updatedTask.getId());
        assertEquals(savedTask.getTitle(), updatedTask.getTitle());
        assertEquals(savedTask.getPriority(), updatedTask.getPriority());
        assertEquals(savedTask.getDescription(), updatedTask.getDescription());
        assertEquals(savedTask.getId(), updatedTask.getId());
        Task foundTask = taskRepository.findById(updatedTask.getId());
        assertNotNull(foundTask.getId());
        assertEquals(foundTask.getId(), updatedTask.getId());
        assertEquals(foundTask.getTitle(),updatedTask.getTitle());
        assertEquals(foundTask.getDescription(),updatedTask.getDescription());
        assertEquals(foundTask.getStatus(),updatedTask.getStatus());
        assertEquals(foundTask.getPriority(),updatedTask.getPriority());
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task("firsTask", "Java and SQL lerning", TaskPriority.MEDIUM);
        Task savedTask = taskRepository.save(task);
        Long taskId = savedTask.getId();
        assertNotNull(taskRepository.findById(taskId));
        taskRepository.delete(taskId);
        assertNull(taskRepository.findById(taskId));
    }
}
