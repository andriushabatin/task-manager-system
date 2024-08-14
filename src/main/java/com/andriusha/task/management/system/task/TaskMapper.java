package com.andriusha.task.management.system.task;

import com.andriusha.task.management.system.comment.CommentMapper;
import com.andriusha.task.management.system.user.User;
import com.andriusha.task.management.system.user.UserMapper;
import com.andriusha.task.management.system.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserService userService;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;

    public TaskReadingDto toReadingDto(Task task) {
        return TaskReadingDto.builder()
                .id(task.getId())
                .heading(task.getHeading())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .author(userMapper.toDto(task.getAuthor()))
                .performer(userMapper.toDto(task.getPerformer()))
                .comments(
                        task.getCommentsList().stream()
                                .map(commentMapper::toDto)
                                .collect(Collectors.toList())
                )
                .build();
    }


    public Task toTask(User author, TaskCreationDto dto) {
        return Task.builder()
                .heading(dto.getHeading())
                .description(dto.getDescription())
                .status(TaskStatus.NOT_STARTED)
                .priority(TaskPriority.valueOf(dto.getPriority()))
                .author(author)
                .performer(userService.getById(Long.parseLong(dto.getPerformerId())))
                .commentsList(Collections.emptyList())
                .build();
    }

    public Task toTask(TaskReadingDto dto) {
        return Task.builder()
                .id(dto.getId())
                .heading(dto.getHeading())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .author(userService.getById(dto.getAuthor().getId()))
                .performer(userService.getById(dto.getPerformer().getId()))
                .build();
    }
}


