package com.andriusha.task.management.system.task;

import com.andriusha.task.management.system.comment.CommentCreationDto;
import com.andriusha.task.management.system.comment.CommentReadingDto;
import com.andriusha.task.management.system.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;

    @PostMapping("/add")
    public TaskReadingDto add(
            @RequestHeader(value = "Authorization") String header,
            @RequestBody TaskCreationDto taskCreationDto
    ) {
        return taskService.addTask(header, taskCreationDto);
    }

    @PatchMapping("/update/{id}")
    public TaskReadingDto update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return taskService.update(id, updates);
    }

    @GetMapping("/get/{id}")
    public TaskReadingDto getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @GetMapping("/get/all")
    public List<TaskReadingDto> getAll() {
        return taskService.getAll();
    }

    //  tasks/get/all-of-author/1
    @GetMapping("/get/all-by-author/{authorId}")
    public List<TaskReadingDto> getAllByAuthorId(@PathVariable Long authorId) {
        return taskService.getAllByAuthorId(authorId);
    }

    @GetMapping("/get-all-by-performer/{id}")
    public List<TaskReadingDto> getAllByPerformerId(@PathVariable Long id) {
        return taskService.getAllByPerformerId(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        return taskService.deleteById(id);
    }

    @DeleteMapping("/delete/all")
    public String deleteAll() {
        return taskService.deleteAll();
    }

    @PostMapping("/comment-task/{taskId}")
    public CommentReadingDto addComment(
            @RequestHeader(value = "Authorization") String header,
            @PathVariable Long taskId,
            @RequestBody  CommentCreationDto commentCreationDto) {
        return commentService.addComment(header, taskId, commentCreationDto);
    }


}
