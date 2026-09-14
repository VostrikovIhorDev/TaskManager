package com.ihor.taskmanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void shouldCreateTask() {
      Task task = new Task("next Task", "do something", TaskPriority.LOW);
      assertEquals("next Task" , task.getTitle());
      assertEquals("do something", task.getDescription());
      assertEquals(TaskPriority.LOW, task.getPriority());
      assertEquals(TaskStatus.TODO, task.getStatus());
      assertNotNull(task.getCreatedAt());
      assertNull(task.getId());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Task("", "do something", TaskPriority.LOW));
        assertThrows(IllegalArgumentException.class, () -> new Task(null, "do something", TaskPriority.LOW));
    }

    @Test
    void shouldThrowExceptionWhenPriorityIsNull() {
        assertThrows(IllegalArgumentException.class, ()-> new Task("next Task", "do something", null));
    }
}
