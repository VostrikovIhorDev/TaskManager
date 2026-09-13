package com.ihor.taskmanager.repository;
import com.ihor.taskmanager.model.Task;
import com.ihor.taskmanager.model.TaskPriority;
import com.ihor.taskmanager.model.TaskStatus;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

   private final String dbURL = System.getenv("DB_URL");
    private final String dbUser = System.getenv("DB_USER");
   private final String dbPassword = System.getenv("DB_PASSWORD");

    public List<Task> findAll() {
        List<Task> taskList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbURL,dbUser,dbPassword)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks;")) {
                try (ResultSet dataOfQuery = preparedStatement.executeQuery()) {
                    while (dataOfQuery.next()) {
                        Long id = dataOfQuery.getLong("id");
                        String title = dataOfQuery.getString("title");
                        String description = dataOfQuery.getString("description");
                        String status = dataOfQuery.getString("status");
                        TaskStatus taskStatus = TaskStatus.valueOf(status);
                        String priority = dataOfQuery.getString("priority");
                        TaskPriority taskPriority = TaskPriority.valueOf(priority);
                        LocalDateTime createdAt = dataOfQuery.getTimestamp("created_at").toLocalDateTime();
                        Task task = new Task(id, title, description, taskStatus, taskPriority, createdAt);
                        taskList.add(task);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return taskList;
    }

    public Task findById(Long taskID) {
        try (Connection connection = DriverManager.getConnection(dbURL,dbUser,dbPassword)) {
            String statement = "SELECT * FROM tasks WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setLong(1, taskID);
                try (ResultSet dataOfQuery = preparedStatement.executeQuery()) {
                    if (dataOfQuery.next()) {
                        Long id = dataOfQuery.getLong("id");
                        String title = dataOfQuery.getString("title");
                        String description = dataOfQuery.getString("description");
                        String status = dataOfQuery.getString("status");
                        TaskStatus taskStatus = TaskStatus.valueOf(status);
                        String priority = dataOfQuery.getString("priority");
                        TaskPriority taskPriority = TaskPriority.valueOf(priority);
                        LocalDateTime createdAt = dataOfQuery.getTimestamp("created_at").toLocalDateTime();
                        Task task = new Task(id, title, description, taskStatus, taskPriority, createdAt);
                        return task;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Task save(Task task) {
        try (Connection connection = DriverManager.getConnection(dbURL,dbUser,dbPassword)) {
            String statement = "INSERT INTO tasks(title, description, status, priority) VALUES(?, ?, ?, ?) RETURNING id, created_at;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, task.getTitle());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setString(3, task.getStatus().toString());
                preparedStatement.setString(4, task.getPriority().toString());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Long id = resultSet.getLong("id");
                        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                        Task savedTask = new Task(id, task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority(), createdAt);
                        return savedTask;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Task update(Task task) {
        try (Connection connection = DriverManager.getConnection(dbURL,dbUser,dbPassword)) {
            String statement = "UPDATE tasks SET title = ?, description = ?, status = ?, priority = ? WHERE id = ? RETURNING title, description, status, priority;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, task.getTitle());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setString(3, task.getStatus().toString());
                preparedStatement.setString(4, task.getPriority().toString());
                preparedStatement.setLong(5, task.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String newTitle = resultSet.getString("title");
                        String newDescription = resultSet.getString("description");
                        TaskStatus newStatus = TaskStatus.valueOf(resultSet.getString("status"));
                        TaskPriority newPriority = TaskPriority.valueOf(resultSet.getString("priority"));
                        Task updatedTask = new Task(task.getId(), newTitle, newDescription, newStatus, newPriority, task.getCreatedAt());
                        return updatedTask;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(dbURL,dbUser,dbPassword)) {
            String statement = "DELETE FROM tasks WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
