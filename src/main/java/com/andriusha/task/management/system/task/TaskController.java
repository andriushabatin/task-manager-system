package com.andriusha.task.management.system.task;

import com.andriusha.task.management.system.comment.CommentCreationDto;
import com.andriusha.task.management.system.comment.CommentReadingDto;
import com.andriusha.task.management.system.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Task methods")
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;

    @Operation(
            summary = "создает новую задачу и сохраняет ее в базу",
            description = "Получает на вход DTO задачи, маппит ее в задачу и сохраняет в базе, " +
                    "возвращая DTO сохраненной задачи."
    )
    @PostMapping("/add")
    public TaskReadingDto add(
            @RequestHeader(value = "Authorization") String header,
            @RequestBody @Valid TaskCreationDto taskCreationDto
    ) {
        return taskService.addTask(header, taskCreationDto);
    }

    @Operation(
            summary = "обновляет указанные поля задачи",
            description = "Получает на вход id задачи для обновления, мапу, где key -- имя поля, а value -- новое значение этого поля, " +
                    "перебирает матрицу, обновляя соответсвующие поля задачи. Поддерживает обновление полей: " +
                    "heading, description, priority и performer -- возращает Reading Dto обновленной задачи."

    )
    @PatchMapping("/update/{id}")
    public TaskReadingDto update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return taskService.update(id, updates);
    }

    @Operation(
            summary = "возвращает задачу по ее id",
            description = "Получает на вход id задачи и возвращает DTO задачи с переданным id."

    )
    @GetMapping("/get/{id}")
    public TaskReadingDto getById(@PathVariable Long id) {
        return taskService.getById(id);
    }


    @Operation(
            summary = "возвращает все задачи",
            description = "Достает из базы список всех сохраненных задач, маппит его в список DTO полученных задач и " +
                    "возвращает его."

    )
    @GetMapping("/get/all")
    public List<TaskReadingDto> getAll() {
        return taskService.getAll();
    }

    @Operation(
            summary = "возвращает все задачи автора",
            description = "Получает на вход id автора, достает из базы список всех задач автора, маппит задачи в Dto объекты " +
                    "и возвращает список этих объектов."

    )
    @GetMapping("/get/all-by-author/{authorId}")
    public List<TaskReadingDto> getAllByAuthorId(@PathVariable Long authorId) {
        return taskService.getAllByAuthorId(authorId);
    }

    @Operation(
            summary = "возвращает все задачи исполнителя",
            description = "Получает на вход id исполнителя, достает из базы список всех задач исполнителя, маппит задачи в Dto объекты " +
                    "и возвращает список этих объектов."

    )
    @GetMapping("/get/all-by-performer/{performerId}")
    public List<TaskReadingDto> getAllByPerformerId(@PathVariable Long performerId) {
        return taskService.getAllByPerformerId(performerId);
    }

    @Operation(
            summary = "удаляет задачу по ее id",
            description = "Получает на вход id задачи, удаляет задачу из базы и возращает сообщение об успешном удалении."

    )
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        return taskService.deleteById(id);
    }

    @Operation(
            summary = "удаляет все задачи",
            description = "Удаляет все задачи из базы и возращает сообщение об успешном удалении."

    )
    @DeleteMapping("/delete/all")
    public String deleteAll() {
        return taskService.deleteAll();
    }

    @Operation(
            summary = "добавляет комментарий к задаче",
            description = "Получает на вход DTO объект комментария и id задачи, маппит DTO в комментарий" +
                    " и добавляет к задаче. Автор комментария задается автоматически, исходя из переданного заголовка" +
                    " Authorization."

    )
    @PostMapping("/comment-task/{taskId}")
    public CommentReadingDto addComment(
            @RequestHeader(value = "Authorization") String header,
            @PathVariable Long taskId,
            @RequestBody @Valid CommentCreationDto commentCreationDto) {
        return commentService.addComment(header, taskId, commentCreationDto);
    }
}
