package com.andriusha.task.management.system.task;

import com.andriusha.task.management.system.user.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskReadingDto {

    private Long id;
    private String heading;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UserDto author;
    private UserDto performer;
}
