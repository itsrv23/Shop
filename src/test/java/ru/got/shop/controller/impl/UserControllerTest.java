package ru.got.shop.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.got.shop.mapper.UserMapperImpl;
import ru.got.shop.model.User;
import ru.got.shop.repository.UserAvatarRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.impl.UserAvatarServiceImpl;
import ru.got.shop.service.impl.UserServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.got.shop.controller.impl.UserControllerFactory.*;


@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private UserServiceImpl userService;
    @SpyBean
    private UserAvatarServiceImpl avatarService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserAvatarRepository userAvatarRepository;

    @SpyBean
    private UserMapperImpl userMapper;


    @Test
    @WithMockUser(username = "admin@gmail.com", password = "adminpass", roles = "USER")
    void getUserUsingGET() throws Exception {
        String path =  "/users/" + getUserEntity().getId();
        String jsonResult = objectMapper.writeValueAsString(userMapper.toDto(getUserEntity()));

        Mockito.when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findFirstByEmail(any(String.class))).thenReturn(Optional.of(getUserEntity()));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(path);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

    }

    @Test
    void getUserMeUsingGET() {
    }

    @Test
    void getUsersUsingGET() {
    }

    @Test
    void setPasswordUsingPOST() {
    }

    @Test
    void updateUserUsingPATCH() {
    }

    @Test
    void updateUserAvatar() {
    }

    @Test
    void getImage() throws Exception {
        String path =  "/images/" + getAvatarEntity().getUuid().toString() + "/";

        Mockito.when(userAvatarRepository.findById(any(UUID.class))).thenReturn(Optional.of(getAvatarEntity()));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(path);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
}