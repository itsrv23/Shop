package ru.got.shop.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.got.shop.mapper.AdsCommentMapper;
import ru.got.shop.mapper.AdsMapper;
import ru.got.shop.mapper.ResponseWrapperAdsCommentMapper;
import ru.got.shop.mapper.UserMapper;
import ru.got.shop.model.AdsComment;
import ru.got.shop.model.dto.AdsCommentDto;
import ru.got.shop.model.dto.ResponseWrapperAdsCommentDto;
import ru.got.shop.repository.AdsCommentRepository;
import ru.got.shop.repository.AdsRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.got.shop.controller.impl.AdsCommentControllerFactory.*;
import static ru.got.shop.controller.impl.UserControllerFactory.*;


@AutoConfigureMockMvc
@SpringBootTest
class AdsCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdsMapper adsMapper;

    @Autowired
    private AdsCommentMapper adsCommentMapper;

    @Autowired
    private ResponseWrapperAdsCommentMapper responseWrapperAdsCommentMapper;

    @Autowired
    private UserMapper userMapper;

    @MockBean
    private AdsRepository adsRepository;

    @MockBean
    private AdsCommentRepository adsCommentRepository;

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "users.read")
    void addComment200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments";
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(getUserCommentEntity());

        AdsCommentDto newCommentDto = AdsCommentDto.builder()
                .text("Подойдет для самолета?")
                .build();

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCommentDto))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "users.read")
    void addComment404TextOfCommentNotFound() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments";

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));

        AdsCommentDto newCommentDto = AdsCommentDto.builder()
                .text(null)
                .build();

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCommentDto))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "users.read")
    void deleteComment200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments/" + getUserCommentEntity().getPk();
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getPk())).thenReturn(Optional.of(getUserCommentEntity()));
        Mockito.doNothing().when(adsCommentRepository).deleteById(getUserCommentEntity().getPk());
        mockMvc.perform(delete(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    @WithMockUser(username = ADMIN_LOGIN,  authorities = "users.read:write")
    void deleteUsersComment200byAdmin() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments/" + getUserCommentEntity().getPk();
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getPk())).thenReturn(Optional.of(getUserCommentEntity()));
        Mockito.doNothing().when(adsCommentRepository).deleteById(getUserCommentEntity().getPk());

        mockMvc.perform(delete(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "users.read")
    void deleteComment403ForbiddenToStrangerUser() throws Exception {
        String path = "/ads/" + getAdminAdsEntity().getPk() + "/comments/" + getAdminCommentEntity().getPk();

        Mockito.when(adsRepository.findById(getAdminAdsEntity().getPk())).thenReturn(Optional.of(getAdminAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getAdminCommentEntity().getPk())).thenReturn(Optional.of(getAdminCommentEntity()));

        mockMvc.perform(delete(path))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getComment200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments/" + getUserCommentEntity().getPk();
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getPk())).thenReturn(Optional.of(getUserCommentEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void getComment404AdsNotFound() throws Exception {
        String path = "/ads/" + 3 + "/comments/" + getUserCommentEntity().getPk();

        Mockito.when(adsRepository.findById(3)).thenReturn(Optional.empty());
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getPk())).thenReturn(Optional.of(getUserCommentEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getComment404CommentNotFound() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments/" + 3;

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(3)).thenReturn(Optional.empty());

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getComment404AdsWithCommentNotFound() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments/" + getAdminCommentEntity().getPk();

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getAdminCommentEntity().getPk())).thenReturn(Optional.of(getAdminCommentEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllComments200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments";
        ResponseWrapperAdsCommentDto responseWrapperAdsCommentDto = new ResponseWrapperAdsCommentDto(1, List.of(adsCommentMapper.toDto(getUserCommentEntity())));
        String jsonResult = objectMapper.writeValueAsString(responseWrapperAdsCommentDto);

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void getAllComments404AdsNotFound() throws Exception {
        String path = "/ads/" + 3 + "/comments";

        Mockito.when(adsRepository.findById(3)).thenReturn(Optional.empty());
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getPk())).thenReturn(Optional.of(getUserCommentEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "users.read")
    void updateComment200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments/" + getUserCommentEntity().getPk();
        String json = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getPk())).thenReturn(Optional.of(getUserCommentEntity()));
        Mockito.when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(getUserCommentEntity());

        mockMvc.perform(patch(path)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @WithMockUser(username = ADMIN_LOGIN,  authorities = "users.read:write")
    void updateUsersComment200ByAdmin() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getPk() + "/comments/" + getUserCommentEntity().getPk();
        String json = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getPk())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getPk())).thenReturn(Optional.of(getUserCommentEntity()));
        Mockito.when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(getUserCommentEntity());

        mockMvc.perform(patch(path)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "users.read")
    void updateComment403ForbiddenToStrangerUser() throws Exception {
        String path = "/ads/" + getAdminAdsEntity().getPk() + "/comments/" + getAdminCommentEntity().getPk();
        String json = objectMapper.writeValueAsString(adsCommentMapper.toDto(getAdminCommentEntity()));

        Mockito.when(adsRepository.findById(getAdminAdsEntity().getPk())).thenReturn(Optional.of(getAdminAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getAdminCommentEntity().getPk())).thenReturn(Optional.of(getAdminCommentEntity()));

        mockMvc.perform(patch(path)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isForbidden());
    }
}