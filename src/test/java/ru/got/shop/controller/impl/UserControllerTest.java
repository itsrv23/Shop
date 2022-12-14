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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    @WithMockUser(username = "admin@gmail.com", password = "adminpass", roles = "ADMIN")
    void getUserUsingGET() throws Exception {
        String path =  "/users/" + getAdminEntity().getId();
        String jsonResult = objectMapper.writeValueAsString(userMapper.toDto(getAdminEntity()));

        Mockito.when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(getAdminEntity()));
        Mockito.when(userRepository.findFirstByEmail(any(String.class))).thenReturn(Optional.of(getAdminEntity()));

        MockHttpServletRequestBuilder requestBuilder = get(path);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "userpass", roles = "USER")
    void getUserUsingGET_200() throws Exception {
        String PATH =  "/users/" + getUserEntity().getId();
        String jsonResult = objectMapper.writeValueAsString(userMapper.toDto(getUserEntity()));

        Mockito.when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findFirstByEmail(any(String.class))).thenReturn(Optional.of(getUserEntity()));

        mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

    }
    @Test
    @WithMockUser(username = "admin@gmail.com", password = "adminpass", roles = "ADMIN")
    void getUserUsingGET_200_admin() throws Exception {
        String path =  "/users/1"; // админ запрашивает акк юзера
        String jsonResult = objectMapper.writeValueAsString(userMapper.toDto(getUserEntity()));
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(getAdminEntity()));
        Mockito.when(userRepository.findFirstByEmail("admin@gmail.com")).thenReturn(Optional.of(getAdminEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

    }

    @Test
    @WithMockUser(username = "user@gmail.com", password = "userpass", roles = "USER")
    void getUserUsingGET_403() throws Exception {
        String path =  "/users/2"; // юзер запрашивает акк админа

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(getAdminEntity()));
        Mockito.when(userRepository.findFirstByEmail("user@gmail.com")).thenReturn(Optional.of(getUserEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isForbidden());

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

        MockHttpServletRequestBuilder requestBuilder = get(path);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
}