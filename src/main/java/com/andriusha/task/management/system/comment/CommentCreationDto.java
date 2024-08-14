package com.andriusha.task.management.system.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreationDto {

    @NotBlank(message = "content id required")
    private String content;
}
