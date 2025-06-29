package com.example.mini_jira.service;

import com.example.mini_jira.entity.Task;
import com.example.mini_jira.entity.TaskRequestDTO;
import com.example.mini_jira.entity.User;
import com.example.mini_jira.repository.TaskRepository;
import com.example.mini_jira.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService
{
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

//    public Task createTask(Task task) {
//
//
//
//        return taskRepository.save(task);
//    }
public Task createTask(TaskRequestDTO dto) {
    User assignedUser = userRepository.findById(dto.getAssignedToId())
            .orElseThrow(() -> new RuntimeException("Assigned user not found with ID: " + dto.getAssignedToId()));

    Task task = new Task();
    task.setTitle(dto.getTitle());
    task.setStatus(dto.getStatus());
    task.setAssignedTo(assignedUser);

    return taskRepository.save(task);
}

    public Page<Task> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task updateTask(Long id, Task updatedTask) {


        // Convert string to enum using valueOf

        Optional<Task> optionalTask = taskRepository.findById(id);
        String statusString = String.valueOf(updatedTask.getStatus());

        // Convert string to enum
        Task.TaskStatus newStatus = Task.TaskStatus.valueOf(statusString);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setStatus(newStatus);

            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found with ID: " + id);
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

}
