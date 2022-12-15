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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.got.shop.mapper.UserMapperImpl;
import ru.got.shop.model.User;
import ru.got.shop.model.UserAvatar;
import ru.got.shop.repository.UserAvatarRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.impl.UserAvatarServiceImpl;
import ru.got.shop.service.impl.UserServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @WithMockUser(username = ADMIN_LOGIN, roles = "ADMIN")
    void getUserUsingGET() throws Exception {
        String path = "/users/" + getAdminEntity().getId();
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
    @WithMockUser(username = USER_LOGIN, roles = "USER")
    void getUserUsingGET_200() throws Exception {
        String PATH = "/users/" + getUserEntity().getId();
        String jsonResult = objectMapper.writeValueAsString(userMapper.toDto(getUserEntity()));

        Mockito.when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findFirstByEmail(any(String.class))).thenReturn(Optional.of(getUserEntity()));

        mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

    }

    @Test
    @WithMockUser(username = ADMIN_LOGIN, roles = "ADMIN")
    void getUserUsingGET_200_admin() throws Exception {
        String path = "/users/1"; // админ запрашивает акк юзера
        String jsonResult = objectMapper.writeValueAsString(userMapper.toDto(getUserEntity()));
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(getAdminEntity()));
        Mockito.when(userRepository.findFirstByEmail(ADMIN_LOGIN)).thenReturn(Optional.of(getAdminEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

    }

    @Test
    @WithMockUser(username = USER_LOGIN, roles = "USER")
    void getUserUsingGET_403() throws Exception {
        String path = "/users/2"; // юзер запрашивает акк админа

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(getAdminEntity()));
        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));

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
    @WithMockUser(username = USER_LOGIN, roles = "USER")
    void updateUserAvatar() throws Exception {
        String path = "/users/me/image";
        MockMultipartFile mff = new MockMultipartFile("image", AVATAR_FILE());
        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(getUserEntity());
        Mockito.when(userAvatarRepository.findFirstByUser(any(User.class))).thenReturn(Optional.of(getAvatarEntity()));
        Mockito.when(userAvatarRepository.save(any(UserAvatar.class))).thenReturn(getAvatarEntity());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(path);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        mockMvc.perform(builder.file(mff))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(getAvatarEntity().getUuid().toString()));
    }

    @Test
    void getImage() throws Exception {
        String path = "/images/" + getAvatarEntity().getUuid().toString() + "/";

        Mockito.when(userAvatarRepository.findById(any(UUID.class))).thenReturn(Optional.of(getAvatarEntity()));

        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().bytes(AVATAR_FILE()));
    }
}