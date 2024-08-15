package com.andriusha.task.management.system.task;

import com.andriusha.task.management.system.comment.Comment;
import com.andriusha.task.management.system.comment.CommentCreationDto;
import com.andriusha.task.management.system.comment.CommentRepository;
import com.andriusha.task.management.system.jwt.JwtService;
import com.andriusha.task.management.system.user.User;
import com.andriusha.task.management.system.user.UserRepository;
import com.andriusha.task.management.system.user.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    private CommentRepository commentRepository;

    private String authHeader;
    private User user;
    private Task task;
    private Comment comment;

    @Before
    public void beforeEachTest() throws Exception {
        userRepository.deleteAll();
        taskRepository.deleteAll();

        user = userRepository.save(createUser());
        task = taskRepository.save(createTask());

        String token = jwtService.generateToken(user);
        authHeader = "Bearer " + token;
    }

    @Test
    public void addTest() throws Exception {
        TaskCreationDto taskCreationDto = TaskCreationDto.builder()
                .heading("heading")
                .description("description")
                .priority("HIGH")
                .performerId(user.getId().toString())
                .build();

        mockMvc.perform(post("/api/v1/tasks/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(taskCreationDto))
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateTest() throws Exception {
        Map<String, Object> updates = new HashMap<>(Map.of(
                "heading", "new heading",
                "description", "new description",
                "status", "DONE",
                "priority", "LOW"));

        mockMvc.perform(patch("/api/v1/tasks/update/{taskId}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updates))
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/get/{taskId}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/get/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllByAuthorIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/get/all-by-author/{authorId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllByPerformerIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/get/all-by-performer/{performerId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/delete/{id}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAllTest() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/delete/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void commentTask() throws Exception {
        CommentCreationDto commentCreationDto = CommentCreationDto.builder()
                .content("comment")
                .build();

        mockMvc.perform(post("/api/v1/tasks/comment-task/{taskId}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commentCreationDto))
                        .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public User createUser() {
        return User.builder()
                .firstname("Andrew")
                .lastname("Batin")
                .email("ab@mail.ru")
                .password(passwordEncoder.encode("1234"))
                .role(UserRole.USER)
                .build();
    }

    public Task createTask() {
        return Task.builder()
                .heading("heading")
                .description("description")
                .priority(TaskPriority.HIGH)
                .author(user)
                .performer(user)
                .commentsList(new ArrayList<>())
                .build();
    }
}

