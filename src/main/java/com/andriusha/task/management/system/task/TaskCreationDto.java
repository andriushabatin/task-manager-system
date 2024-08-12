package com.andriusha.task.management.system.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreationDto {

    private Long id;
    private String heading;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    //private Long authorId;
    private Long performerId;
}
