package com.andriusha.task.management.system.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentCreationDto {

    private Long id;
    private String content;
}
