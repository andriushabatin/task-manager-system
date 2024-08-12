package com.andriusha.task.management.system.user;

import com.andriusha.task.management.system.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .authorOfTasks(
                        user.getAuthorOfTasksList()
                                .stream()
                                .map(Task::getId)
                                .collect(Collectors.toList())
                )
                .performerOfTasks(
                        user.getPerformerOfTasksList()
                                .stream()
                                .map(Task::getId)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
