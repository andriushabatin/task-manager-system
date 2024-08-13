package com.andriusha.task.management.system.comment;

import com.andriusha.task.management.system.task.Task;
import com.andriusha.task.management.system.user.User;
import com.andriusha.task.management.system.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentReadingDto toDto(Comment comment) {
        return CommentReadingDto.builder()
                .id(comment.getId())
                .taskId(comment.getTask().getId())
                .content(comment.getContent())
                .author(userMapper.toDto(comment.getAuthor()))
                .build();
    }

    public Comment toComment(User author, Task task, CommentCreationDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .task(task)
                .author(author)
                .build();
    }
}
