package com.andriusha.task.management.system.comment;

import com.andriusha.task.management.system.jwt.JwtService;
import com.andriusha.task.management.system.task.Task;
import com.andriusha.task.management.system.task.TaskRepository;
import com.andriusha.task.management.system.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final JwtService jwtService;
    private final CommentMapper commentMapper;

    public CommentReadingDto addComment(String authHeader, Long taskId, CommentCreationDto commentDto) {
        String jwt = authHeader.substring(7);

        User author = jwtService.extractUser(jwt);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Error: Not found Task with id=" + taskId));

        return commentMapper.toDto(
                commentRepository.save(
                        commentMapper.toComment(author, task, commentDto)
                )
        );
    }
}
