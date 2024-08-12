package com.andriusha.task.management.system.task;

import com.andriusha.task.management.system.user.UserMapper;
import com.andriusha.task.management.system.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserService userService;
    private final UserMapper mapper;

    public TaskReadingDto toReadingDto(Task task) {
        return TaskReadingDto.builder()
                .id(task.getId())
                .heading(task.getHeading())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .author(mapper.toDto(task.getAuthor()))
                .performer(mapper.toDto(task.getPerformer()))
                .build();
    }


    public Task toTask(TaskCreationDto dto) {
        return Task.builder()
                .id(dto.getId())
                .heading(dto.getHeading())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .author(userService.getById(dto.getAuthorId()))
                .performer(userService.getById(dto.getPerformerId()))
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


