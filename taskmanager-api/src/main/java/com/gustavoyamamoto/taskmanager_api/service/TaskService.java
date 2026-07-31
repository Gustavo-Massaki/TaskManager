package com.gustavoyamamoto.taskmanager_api.service;

import com.gustavoyamamoto.taskmanager_api.entity.Task;
import com.gustavoyamamoto.taskmanager_api.exception.InvalidTaskException;
import com.gustavoyamamoto.taskmanager_api.exception.TaskNotFoundException;
import com.gustavoyamamoto.taskmanager_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        normalizeTask(task);
        validateTask(task);
        prepareTask(task);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(){
         return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task não encontrada"));
    }

    public Task updateTask(Long id, Task updatedTask){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task não encontrada"));
        normalizeTask(updatedTask);
        validateTask(updatedTask);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setCompleted(updatedTask.isCompleted());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        if (!taskRepository.existsById(id)){
            throw new TaskNotFoundException("Task não encontrada");
        }
        taskRepository.deleteById(id);
    }

    private void validateTask(Task task){
        if (task.getTitle() == null || task.getTitle().isBlank()){
            throw new InvalidTaskException("Título é obrigatório");
        }

        if (task.getTitle().length() > 100){
            throw new InvalidTaskException("Título deve possuir no máximo 100 caracteres");
        }

        if (task.getDescription() != null && task.getDescription().length() > 500){
            throw new InvalidTaskException("Descrição deve possuir no máximo 500 caracteres");
        }

        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())){
            throw new InvalidTaskException("Data inválida");
        }
    }

    private void prepareTask(Task task){
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);
    }

    private void normalizeTask(Task task){
        if (task.getTitle() != null){
            task.setTitle(task.getTitle().strip());
        }
    }
}