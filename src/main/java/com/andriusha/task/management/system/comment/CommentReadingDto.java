package com.andriusha.task.management.system.comment;

import com.andriusha.task.management.system.user.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentReadingDto {

    Long id;
    String content;
    UserDto author;
}
