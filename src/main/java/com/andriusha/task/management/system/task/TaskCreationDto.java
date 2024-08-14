package com.andriusha.task.management.system.task;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreationDto {

    @NotBlank(message = "heading is required")
    private String heading;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "priority is required")
    private String priority;
    @NotBlank(message = "performerId is required")
    private String performerId;
}
