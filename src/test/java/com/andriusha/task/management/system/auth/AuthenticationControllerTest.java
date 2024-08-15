package com.andriusha.task.management.system.auth;

import com.andriusha.task.management.system.user.User;
import com.andriusha.task.management.system.user.UserRepository;
import com.andriusha.task.management.system.user.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AuthenticationService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Getter
    private static String jwt;

    @Test
    public void registerTest() throws Exception {
        userRepository.deleteAll();

        RegisterRequest request = RegisterRequest.builder()
                .firstname("Andrew")
                .lastname("Batin")
                .email("ab@mail.ru")
                .password("1234")
                .build();

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void authenticationTest() throws Exception {
        userRepository.deleteAll();

        User user = User.builder()
                .firstname("Andrew")
                .lastname("Batin")
                .email("ab@mail.ru")
                .password(passwordEncoder.encode("1234"))
                .role(UserRole.USER)
                .build();
        userRepository.save(user);

        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("ab@mail.ru")
                .password("1234")
                .build();

        mockMvc.perform(post("/api/v1/auth/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
