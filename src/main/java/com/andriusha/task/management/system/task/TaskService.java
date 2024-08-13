package com.andriusha.task.management.system.task;

import com.andriusha.task.management.system.comment.CommentMapper;
import com.andriusha.task.management.system.comment.CommentRepository;
import com.andriusha.task.management.system.jwt.JwtService;
import com.andriusha.task.management.system.user.User;
import com.andriusha.task.management.system.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepo;
    private final TaskMapper taskMapper;
    private final CommentMapper commentMapper;
    private final JwtService jwtService;

    public TaskReadingDto addTask(String authHeader,TaskCreationDto taskCreationDto) {
        String jwt = authHeader.substring(7);
        User author = jwtService.extractUser(jwt);

        return taskMapper.toReadingDto(
                taskRepository.save(
                        taskMapper.toTask(author, taskCreationDto)
                )
        );
    }

    public TaskReadingDto update(Long id, Map<String, Object> updates) {
        Task task = taskMapper.toTask(getById(id));

        updates.forEach((key, value) -> {
                    switch (key) {
                        case "heading" -> {
                            task.setHeading((String) value);
                        }
                        case "description" -> {
                            task.setDescription((String) value);
                        }
                        case "status" -> {
                            task.setStatus(TaskStatus.valueOf((String)value));
                        }
                        case "priority" -> {
                            task.setPriority(TaskPriority.valueOf((String) value));
                        }
                        default -> {
                            throw new IllegalArgumentException("Invalid field: " + key);
                        }
                    }
                }
                );

        return taskMapper.toReadingDto(taskRepository.save(task));
    }

    public TaskReadingDto getById(Long id) {
        return taskMapper.toReadingDto(taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"))
        );
    }

    public List<TaskReadingDto> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toReadingDto)
                .collect(Collectors.toList());
    }

    public List<TaskReadingDto> getAllByAuthorId(Long authorId) {
        User author = userRepository.findById(authorId).orElseThrow();

        return author.getAuthorOfTasksList().stream()
                .map(taskMapper::toReadingDto)
                .collect(Collectors.toList());
    }

    public List<TaskReadingDto> getAllByPerformerId(Long id) {
        User performer = userRepository.findById(id).orElseThrow();

        return performer.getPerformerOfTasksList().stream()
                .map(taskMapper::toReadingDto)
                .collect(Collectors.toList());
    }

    public String deleteById(Long id) {
        taskRepository.deleteById(id);
        return "Task deleted";
    }

    public String deleteAll() {
        taskRepository.deleteAll();
        return "All tasks deleted";
    }
}
