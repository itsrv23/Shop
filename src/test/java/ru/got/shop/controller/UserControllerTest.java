package ru.got.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.got.shop.mapper.UserMapperImpl;
import ru.got.shop.model.User;
import ru.got.shop.model.UserAvatar;
import ru.got.shop.dto.NewPasswordDto;
import ru.got.shop.dto.ResponseWrapperUserDto;
import ru.got.shop.repository.UserAvatarRepository;
import ru.got.shop.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.got.shop.controller.UserControllerFactory.*;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserAvatarRepository userAvatarRepository;
    @SpyBean
    private UserMapperImpl userMapper;


    @Test
    @WithMockUser(username = ADMIN_LOGIN,  authorities = "users.read:write")
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
    @WithMockUser(username = USER_LOGIN,  authorities = "users.read")
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
    @WithMockUser(username = ADMIN_LOGIN,  authorities = "users.read:write")
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
    @WithMockUser(username = USER_LOGIN,  authorities = "users.read")
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
    @WithMockUser(username = USER_LOGIN,  authorities = "users.read")
    void getUserMeUsingGET_200() throws Exception {
        String path = "/users/me";
        String jsonResult = objectMapper.writeValueAsString(userMapper.toDto(getUserEntity()));

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(getAdminEntity()));
        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void getUserMeUsingGET_400() throws Exception {
        String path = "/users/me";

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(getAdminEntity()));
        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"Access is denied\""));
    }

    @Test
    @WithMockUser(username = ADMIN_LOGIN,  authorities = "users.read:write")
    void getUsersUsingGET_200() throws Exception{
        // todo  не используется на фронте
        String path = "/users";
        ResponseWrapperUserDto responseWrapperUserDto = new ResponseWrapperUserDto(1, List.of(userMapper.toDto(getUserEntity())));
        String json = objectMapper.writeValueAsString(responseWrapperUserDto);

        Mockito.when(userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(List.of(getUserEntity()));

        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "users.read")
    void getUsersUsingGET_400() throws Exception{
        // todo  не используется на фронте
        String path = "/users";

        mockMvc.perform(get(path))
                .andExpect(status().is(400))
                .andExpect(content().string("\"Access is denied\""));
    }

    @Test
    @WithMockUser(username = USER_LOGIN,  authorities = "users.read")
    void setPasswordUsingPOST_200() throws Exception {
        String path = "/users/set_password";
        String json = objectMapper.writeValueAsString(getNewPasswordDto());

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(getUserEntity());

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

    }

    @Test
    void setPasswordUsingPOST_401() throws Exception {
        String path = "/users/set_password";
        String json = objectMapper.writeValueAsString(getNewPasswordDto());

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(getUserEntity());

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(401));

    }

    @Test
    @WithMockUser(username = USER_LOGIN,  authorities = "users.read")
    void setPasswordUsingPOST_403() throws Exception {
        String path = "/users/set_password";
        NewPasswordDto newPasswordDto = getNewPasswordDto();
        newPasswordDto.setCurrentPassword("WRONG OLD PASS");
        String json = objectMapper.writeValueAsString(newPasswordDto);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(getUserEntity());

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = USER_LOGIN,  authorities = "users.read")
    void updateUserUsingPATCH() throws Exception {
        String path = "/users/me";
        String json = objectMapper.writeValueAsString(userMapper.toDto(getUserEntity()));

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(getUserEntity());

        mockMvc.perform(patch(path)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(json));

    }

    @Test
    @WithMockUser(username = USER_LOGIN,  authorities = "users.read")
    void updateUserAvatar() throws Exception {
        String path = "/users/me/image";
        MockMultipartFile mff = new MockMultipartFile("image", AVATAR_FILE());
        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(getUserEntity());
        Mockito.when(userAvatarRepository.findFirstByUser(any(User.class))).thenReturn(Optional.of(getAvatarEntity()));
        Mockito.when(userAvatarRepository.save(any(UserAvatar.class))).thenReturn(getAvatarEntity());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(path);
        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mockMvc.perform(builder.file(mff))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(getAvatarEntity().getUuid().toString()));
    }
    @Test
    @WithMockUser(username = USER_LOGIN,  authorities = "users.read")
    void updateUserAvatar_BigFile() throws Exception {
        String path = "/users/me/image";
        MockMultipartFile mff = new MockMultipartFile("image", AVATAR_FILE_BIG_FILE());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(path);
        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mockMvc.perform(builder.file(mff))
                .andDo(print())
                .andExpect(status().isIAmATeapot());
    }

    @Test
    @WithMockUser(username = USER_LOGIN,  authorities = "users.read")
    void updateUserAvatar_IOException() throws Exception {
        String path = "/users/me/image";
        CustomMockMultipartFile mff = new CustomMockMultipartFile("image", AVATAR_FILE());

        Mockito.when(userRepository.findFirstByEmail(USER_LOGIN)).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(getUserEntity());
        Mockito.when(userAvatarRepository.findFirstByUser(any(User.class))).thenReturn(Optional.of(getAvatarEntity()));
        Mockito.when(userAvatarRepository.save(any(UserAvatar.class))).thenReturn(getAvatarEntity());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(path);
        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mockMvc.perform(builder.file(mff))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"IOException, please repeat request again\""));
    }

    @Test
    void getImage() throws Exception {
        String path = "/images/" + getAvatarEntity().getUuid().toString() + "/";

        Mockito.when(userAvatarRepository.findById(any(UUID.class))).thenReturn(Optional.of(getAvatarEntity()));

        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().bytes(AVATAR_FILE()));
    }

    private class CustomMockMultipartFile extends MockMultipartFile {
        public CustomMockMultipartFile(String name, byte[] content) {
            super(name, content);
        }

        @Override
        public byte[] getBytes() throws IOException {
            throw new IOException();
        }
    }
}