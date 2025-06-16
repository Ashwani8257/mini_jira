package com.example.mini_jira.controller;

import com.example.mini_jira.entity.Task;
import com.example.mini_jira.repository.TaskRepository;
import com.example.mini_jira.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public Page<Task> getTasks(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        if (keyword != null && !keyword.isEmpty()) {
            return taskRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        }
        return taskRepository.findAll(pageable);
    }


    @PostMapping("/create/task")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
}
