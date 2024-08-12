package com.andriusha.task.management.system.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private Long id;
    private String email;
    private List<Long> authorOfTasks;
    private List<Long> performerOfTasks;
}

