package ru.got.shop.controller;

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
import ru.got.shop.dto.AdsCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;
import ru.got.shop.mapper.AdsCommentMapper;
import ru.got.shop.model.AdsComment;
import ru.got.shop.repository.AdsCommentRepository;
import ru.got.shop.repository.AdsRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.got.shop.controller.AdsCommentControllerFactory.*;
import static ru.got.shop.controller.UserControllerFactory.ADMIN_LOGIN;
import static ru.got.shop.controller.UserControllerFactory.USER_LOGIN;

@AutoConfigureMockMvc
@SpringBootTest
class AdsCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdsCommentMapper adsCommentMapper;

    @MockBean
    private AdsRepository adsRepository;

    @MockBean
    private AdsCommentRepository adsCommentRepository;

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.comment.crud")
    void addComment200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments";
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));
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
    @WithMockUser(username = ADMIN_LOGIN, authorities = "ads.comment.full")
    void addComment200byAdmin() throws Exception {
        String path = "/ads/" + getAdminAdsEntity().getId() + "/comments";
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getAdminCommentEntity()));

        Mockito.when(adsRepository.findById(getAdminAdsEntity().getId())).thenReturn(Optional.of(getAdminAdsEntity()));
        Mockito.when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(getAdminCommentEntity());

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
    @WithMockUser(username = USER_LOGIN, authorities = "ads.comment.crud")
    void addComment404TextOfCommentNotFound() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments";

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));

        AdsCommentDto newCommentDto = AdsCommentDto.builder()
                .text(null)
                .build();

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCommentDto))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.comment.crud")
    void deleteComment200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments/" + getUserCommentEntity().getId();
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getId())).thenReturn(Optional.of(getUserCommentEntity()));
        Mockito.doNothing().when(adsCommentRepository).deleteById(getUserCommentEntity().getId());
        mockMvc.perform(delete(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    @WithMockUser(username = ADMIN_LOGIN, authorities = "ads.comment.full")
    void deleteComment200byAdmin() throws Exception {
        String path = "/ads/" + getAdminAdsEntity().getId() + "/comments/" + getAdminCommentEntity().getId();
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getAdminCommentEntity()));

        Mockito.when(adsRepository.findById(getAdminAdsEntity().getId())).thenReturn(Optional.of(getAdminAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getAdminCommentEntity().getId())).thenReturn(Optional.of(getAdminCommentEntity()));
        Mockito.doNothing().when(adsCommentRepository).deleteById(getAdminCommentEntity().getId());
        mockMvc.perform(delete(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    @WithMockUser(username = ADMIN_LOGIN, authorities = "ads.comment.full")
    void deleteUsersComment200byAdmin() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments/" + getUserCommentEntity().getId();
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getId())).thenReturn(Optional.of(getUserCommentEntity()));
        Mockito.doNothing().when(adsCommentRepository).deleteById(getUserCommentEntity().getId());

        mockMvc.perform(delete(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.comment.crud")
    void deleteComment403ForbiddenToStrangerUser() throws Exception {
        String path = "/ads/" + getAdminAdsEntity().getId() + "/comments/" + getAdminCommentEntity().getId();

        Mockito.when(adsRepository.findById(getAdminAdsEntity().getId())).thenReturn(Optional.of(getAdminAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getAdminCommentEntity().getId())).thenReturn(Optional.of(getAdminCommentEntity()));

        mockMvc.perform(delete(path))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getComment200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments/" + getUserCommentEntity().getId();
        String jsonResult = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getId())).thenReturn(Optional.of(getUserCommentEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void getComment404AdsNotFound() throws Exception {
        String path = "/ads/" + 3 + "/comments/" + getUserCommentEntity().getId();

        Mockito.when(adsRepository.findById(3)).thenReturn(Optional.empty());
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getId())).thenReturn(Optional.of(getUserCommentEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getComment404CommentNotFound() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments/" + 3;

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(3)).thenReturn(Optional.empty());

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getComment404AdsWithCommentNotFound() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments/" + getAdminCommentEntity().getId();

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getAdminCommentEntity().getId())).thenReturn(Optional.of(getAdminCommentEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllComments200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments";
        ResponseWrapperAdsCommentDto responseWrapperAdsCommentDto = new ResponseWrapperAdsCommentDto(1, List.of(adsCommentMapper.toDto(getUserCommentEntity())));
        String jsonResult = objectMapper.writeValueAsString(responseWrapperAdsCommentDto);

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void getAllComments404AdsNotFound() throws Exception {
        String path = "/ads/" + 3 + "/comments";

        Mockito.when(adsRepository.findById(3)).thenReturn(Optional.empty());
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getId())).thenReturn(Optional.of(getUserCommentEntity()));

        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.comment.crud")
    void updateComment200() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments/" + getUserCommentEntity().getId();
        String json = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getId())).thenReturn(Optional.of(getUserCommentEntity()));
        Mockito.when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(getUserCommentEntity());

        mockMvc.perform(patch(path)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @WithMockUser(username = ADMIN_LOGIN, authorities = "ads.comment.full")
    void updateComment200byAdmin() throws Exception {
        String path = "/ads/" + getAdminAdsEntity().getId() + "/comments/" + getAdminCommentEntity().getId();
        String json = objectMapper.writeValueAsString(adsCommentMapper.toDto(getAdminCommentEntity()));

        Mockito.when(adsRepository.findById(getAdminAdsEntity().getId())).thenReturn(Optional.of(getAdminAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getAdminCommentEntity().getId())).thenReturn(Optional.of(getAdminCommentEntity()));
        Mockito.when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(getAdminCommentEntity());

        mockMvc.perform(patch(path)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @WithMockUser(username = ADMIN_LOGIN, authorities = "ads.comment.full")
    void updateUsersComment200ByAdmin() throws Exception {
        String path = "/ads/" + getUserAdsEntity().getId() + "/comments/" + getUserCommentEntity().getId();
        String json = objectMapper.writeValueAsString(adsCommentMapper.toDto(getUserCommentEntity()));

        Mockito.when(adsRepository.findById(getUserAdsEntity().getId())).thenReturn(Optional.of(getUserAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getUserCommentEntity().getId())).thenReturn(Optional.of(getUserCommentEntity()));
        Mockito.when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(getUserCommentEntity());

        mockMvc.perform(patch(path)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.comment.crud")
    void updateComment403ForbiddenToStrangerUser() throws Exception {
        String path = "/ads/" + getAdminAdsEntity().getId() + "/comments/" + getAdminCommentEntity().getId();
        String json = objectMapper.writeValueAsString(adsCommentMapper.toDto(getAdminCommentEntity()));

        Mockito.when(adsRepository.findById(getAdminAdsEntity().getId())).thenReturn(Optional.of(getAdminAdsEntity()));
        Mockito.when(adsCommentRepository.findById(getAdminCommentEntity().getId())).thenReturn(Optional.of(getAdminCommentEntity()));

        mockMvc.perform(patch(path)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }
}