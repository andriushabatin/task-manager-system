package com.andriusha.task.management.system.task;

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
    private final TaskMapper taskMapper;
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
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error: Not found task with id=" + id));

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
                        case "performer" -> {
                            Long performerId = Long.parseLong((String) value);
                            task.setPerformer(
                                    userRepository.findById(performerId)
                                            .orElseThrow(() -> new IllegalArgumentException(
                                                    "Error: Not found user with id=" + performerId)
                                            )
                            );
                        }
                        default -> {
                            throw new IllegalArgumentException("Error: Invalid field '" + key + "'");
                        }
                    }
                }
                );

        return taskMapper.toReadingDto(taskRepository.save(task));
    }

    public TaskReadingDto getById(Long id) {
        return taskMapper.toReadingDto(taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error: Not found task with id=" + id))
        );
    }

    public List<TaskReadingDto> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toReadingDto)
                .collect(Collectors.toList());
    }

    public List<TaskReadingDto> getAllByAuthorId(Long id) {
        User author = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Error: Not found User with id=" + id)
        );

        return author.getAuthorOfTasksList().stream()
                .map(taskMapper::toReadingDto)
                .collect(Collectors.toList());
    }

    public List<TaskReadingDto> getAllByPerformerId(Long id) {
        User performer = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Error: Not found User with id=" + id)
        );

        return performer.getPerformerOfTasksList().stream()
                .map(taskMapper::toReadingDto)
                .collect(Collectors.toList());
    }

    public String deleteById(Long id) {
        getById(id);
        taskRepository.deleteById(id);
        return "Task deleted";
    }

    public String deleteAll() {
        List<Task> tasks = taskRepository.findAll();

        if(tasks.isEmpty()) {
            return "No tasks to delete";
        } else {
            taskRepository.deleteAll();
            return "All tasks deleted";
        }
    }
}
