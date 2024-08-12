package com.andriusha.task.management.system.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskReadingDto addTask(TaskCreationDto taskCreationDto) {
        return mapper.toReadingDto(
                repository.save(
                        mapper.toTask(taskCreationDto)
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

        return mapper.toReadingDto(repository.save(task));
    }

    public TaskReadingDto getById(Long id) {
        return mapper.toReadingDto(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"))
        );
    }

    public List<TaskReadingDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toReadingDto)
                .collect(Collectors.toList());
    }

    public String deleteById(Long id) {
        repository.deleteById(id);
        return "Task deleted";
    }

    public String deleteAll() {
        repository.deleteAll();
        return "All tasks deleted";
    }
}
