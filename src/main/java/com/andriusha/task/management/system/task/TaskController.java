package com.andriusha.task.management.system.task;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/add")
    public TaskReadingDto add(@RequestBody TaskCreationDto taskCreationDto) {
        return service.addTask(taskCreationDto);
    }

    @PatchMapping("/update/{id}")
    public TaskReadingDto update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return service.update(id, updates);
    }

    @GetMapping("/get/{id}")
    public TaskReadingDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/get/all")
    public List<TaskReadingDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/delete/all")
    public String deleteAll() {
        return service.deleteAll();
    }
}
