package com.ihor.taskmanager.service;

import com.ihor.taskmanager.exceptions.TaskNotFoundException;
import com.ihor.taskmanager.model.Task;
import com.ihor.taskmanager.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(Long taskId) {
        Task foundTask = taskRepository.findById(taskId);
        if(foundTask == null) {
            throw new TaskNotFoundException("Task with id " + taskId + " not found");
        }
        return foundTask;
    }

    public List<Task> findAll() {
        List<Task> tasksList = taskRepository.findAll();
        return tasksList;
    }

    public Task create(Task task) {
        Task createdTask = taskRepository.save(task);
        if(createdTask == null) {
            throw new TaskNotFoundException("Task doesn't exists");
        }
        return createdTask;
    }

    public Task update(Task task) {
        if(task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        Task updatedTask = taskRepository.update(task);
        if(updatedTask == null) {
            throw new TaskNotFoundException("Task with id " + task.getId() + " not found and can't be updated");
        }
        return updatedTask;
    }

    public void delete(Long id) {
        Task foundTask = taskRepository.findById(id);
        if(foundTask == null) {
            throw new TaskNotFoundException("Task with id " + id + " not found");
        }
        taskRepository.delete(id);
    }


}
