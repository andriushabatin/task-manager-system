package com.andriusha.task.management.system.task;

import com.andriusha.task.management.system.comment.CommentReadingDto;
import com.andriusha.task.management.system.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    private List<CommentReadingDto> comments;
}
