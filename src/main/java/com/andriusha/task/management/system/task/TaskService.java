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
    private final TaskMapper mapper;
    private final JwtService jwtService;

    public TaskReadingDto addTask(String authHeader,TaskCreationDto taskCreationDto) {
        String jwt = authHeader.substring(7);
        String authorEmail = jwtService.extractUsername(jwt);
        User author = userRepository.findByEmail(authorEmail).orElseThrow();

        return mapper.toReadingDto(
                taskRepository.save(
                        mapper.toTask(author, taskCreationDto)
                )
        );
    }

    public TaskReadingDto update(Long id, Map<String, Object> updates) {
        Task task = mapper.toTask(getById(id));

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

        return mapper.toReadingDto(taskRepository.save(task));
    }

    public TaskReadingDto getById(Long id) {
        return mapper.toReadingDto(taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"))
        );
    }

    public List<TaskReadingDto> getAll() {
        return taskRepository.findAll().stream()
                .map(mapper::toReadingDto)
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
