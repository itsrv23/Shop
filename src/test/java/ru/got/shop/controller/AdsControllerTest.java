package ru.got.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import ru.got.shop.dto.AdCreateDto;
import ru.got.shop.dto.AdDto;
import ru.got.shop.dto.FullAdDto;
import ru.got.shop.dto.ResponseWrapperAdsDto;
import ru.got.shop.exception.ForbiddenException;
import ru.got.shop.mapper.AdsMapperImpl;
import ru.got.shop.mapper.FullAdsMapperImpl;
import ru.got.shop.model.Ads;
import ru.got.shop.model.Picture;
import ru.got.shop.model.User;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.PictureRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.security.PermissionService;
import ru.got.shop.service.impl.AdsServiceImpl;
import ru.got.shop.service.impl.PictureDiskServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.got.shop.controller.UserControllerFactory.AVATAR_FILE;
import static ru.got.shop.controller.UserControllerFactory.USER_LOGIN;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class AdsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private AdsMapperImpl adsMapper;
    @SpyBean
    private FullAdsMapperImpl fullAdsMapper;
    @SpyBean
    private ObjectMapper mapper;
    @MockBean
    private PictureRepository pictureRepository;
    @MockBean
    private AdsRepository adsRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean(name = "pictureDiskServiceImpl")
    private PictureDiskServiceImpl pictureDiskServiceImpl;
    @SpyBean
    private AdsServiceImpl service;
    @MockBean
    private PermissionService permissionService;

    private final String AD_ID = "/ads/{id}";
    private final String ADS = "/ads";
    private final String ADS_ME = "/ads/me";
    private Ads ads;
    private AdDto adDto;
    private String json;
    private Picture picture;
    private User user;

    @BeforeEach
    void init() throws Exception {
        ads = AdsCommentControllerFactory.getUserAdsEntity();
        picture = AdsFactory.getPicture();
        ads.setPicture(picture);
        adDto = adsMapper.toDto(ads);
        user = UserControllerFactory.getUserEntity();
    }

    @Test
    void getAllAd_200() throws Exception {
        json = mapper.writeValueAsString(new ResponseWrapperAdsDto(1, List.of(adDto)));
        when(adsRepository.findAll()).thenReturn(List.of(ads));

        mockMvc.perform(get(ADS).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void addAd_201() throws Exception {
        AdCreateDto createAdDto = AdsFactory.getCreateAdsDto();
        String createJson = mapper.writeValueAsString(createAdDto);
        String adJson = mapper.writeValueAsString(adDto);

        MockMultipartFile jsonValue = new MockMultipartFile("properties",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                createJson.getBytes());
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(service.addAd(createAdDto, jsonValue)).thenReturn(adDto);

        mockMvc.perform(multipart(ADS).file(jsonValue).file("image", AVATAR_FILE()).param("file", "image"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(adJson));
    }

    @Test
    void addAd_400() throws Exception {
        AdCreateDto createAdDto = AdsFactory.getCreateAdsDto();
        String createJson = mapper.writeValueAsString(createAdDto);

        MockMultipartFile jsonValue = new MockMultipartFile("properties",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                createJson.getBytes());

        mockMvc.perform(multipart(ADS).file(jsonValue).file("image", AVATAR_FILE()).param("file", "image"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void editPicture_200() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", AVATAR_FILE());
        json = mapper.writeValueAsString(adDto);

        MockMultipartHttpServletRequestBuilder builder = multipart("/ads/1/image");
        builder.with(request -> {
            request.setMethod("PATCH");
            request.addParameter("image");
            return request;
        });
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(adsRepository.save(ads)).thenReturn(ads);
        when(pictureRepository.save(picture)).thenReturn(picture);
        when(adsRepository.findById(1)).thenReturn(Optional.of(ads));

        mockMvc.perform(builder.file(file)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAdImage_200() throws Exception {
        String filePath = "src/test/resources/user_avatar_big.jpg";
        byte[] data = Files.readAllBytes(Path.of(filePath));
        picture.setFilePath(filePath);
        String adsImg = "/ads/image/eeaffff0-c242-41cb-9baf-44ed689c2c25";

        when(pictureRepository.findByUuid(UUID.randomUUID())).thenReturn(Optional.of(picture));
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(pictureDiskServiceImpl.upload(any(UUID.class))).thenReturn(data);

        mockMvc.perform(get(adsImg).accept(MediaType.IMAGE_PNG)).andDo(print()).andExpect(status().isOk()).andExpect(
                content().bytes(data));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void getMyAds_200() throws Exception {
        json = mapper.writeValueAsString(new ResponseWrapperAdsDto(1, List.of(adDto)));

        when(adsRepository.findAllByUserId(user)).thenReturn(List.of(ads));
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(get(ADS_ME).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void getMyAds_401() throws Exception {
        when(adsRepository.findAllByUserId(user)).thenReturn(List.of(ads));
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(get(ADS_ME).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void getMyAds_404() throws Exception {
        when(adsRepository.findAllByUserId(user)).thenReturn(Collections.emptyList());
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(get(ADS_ME).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void getFullAd_200() throws Exception {
        FullAdDto fullAdDto = fullAdsMapper.toDto(user, ads);
        String json = mapper.writeValueAsString(fullAdDto);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(adsRepository.findById(1)).thenReturn(Optional.of(ads));

        mockMvc.perform(get(AD_ID, 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void getFullAd_404() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        when(adsRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get(AD_ID, 11).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getFullAd_401() throws Exception {
        mockMvc.perform(get(AD_ID, 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void removeAd_204() throws Exception {
        when(adsRepository.findById(1)).thenReturn(Optional.ofNullable(ads));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(permissionService.checkAllowedForbidden(1)).thenReturn(true);

        mockMvc.perform(delete(AD_ID, 1)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void removeAd_401() throws Exception {
        when(adsRepository.findById(1)).thenReturn(Optional.of(ads));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(permissionService.checkAllowedForbidden(1)).thenReturn(true);

        mockMvc.perform(delete(AD_ID, 1)).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void removeAd_403() throws Exception {
        when(adsRepository.findById(1)).thenReturn(Optional.ofNullable(ads));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(permissionService.checkAllowedForbidden(1)).thenThrow(new ForbiddenException());

        mockMvc.perform(delete(AD_ID, 1)).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void updateAd_200() throws Exception {
        String path = "/ads/{id}";
        AdDto adDto = AdsFactory.getAdDto();
        AdCreateDto adCreateDto = AdsFactory.getCreateAdsDto();
        String createJson = mapper.writeValueAsString(adCreateDto);
        String AdJson = mapper.writeValueAsString(adDto);

        when(adsRepository.findById(1)).thenReturn(Optional.ofNullable(ads));
        when(service.updateAd(1, adCreateDto)).thenReturn(adDto);

        mockMvc.perform(patch(path, 1).content(createJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().json(
                AdJson));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void updateAd_404() throws Exception {
        String path = "/ads/{id}";
        AdCreateDto adCreateDto = AdsFactory.getCreateAdsDto();
        String createJson = mapper.writeValueAsString(adCreateDto);

        when(adsRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(patch(path, 1).content(createJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void updateAd_401() throws Exception {
        String path = "/ads/{id}";
        AdCreateDto adCreateDto = AdsFactory.getCreateAdsDto();
        String createJson = mapper.writeValueAsString(adCreateDto);

        mockMvc.perform(patch(path, 1).content(createJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void updateAd_403() throws Exception {
        String path = "/ads/{id}";
        AdCreateDto adCreateDto = AdsFactory.getCreateAdsDto();
        String createJson = mapper.writeValueAsString(adCreateDto);

        when(adsRepository.findById(1)).thenReturn(Optional.ofNullable(ads));
        when(permissionService.checkAllowedForbidden(1)).thenThrow(new ForbiddenException());

        mockMvc.perform(patch(path, 1).content(createJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isForbidden());
    }
}