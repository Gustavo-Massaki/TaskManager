package com.gustavoyamamoto.taskmanager_api.service;

import com.gustavoyamamoto.taskmanager_api.entity.Task;
import com.gustavoyamamoto.taskmanager_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task){
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(){
         return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task updatedTask){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task não encontrada"));
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setCompleted(updatedTask.isCompleted());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
