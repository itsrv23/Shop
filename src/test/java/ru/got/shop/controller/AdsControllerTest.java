package ru.got.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.got.shop.dto.AdDto;
import ru.got.shop.dto.FullAdDto;
import ru.got.shop.dto.ResponseWrapperAdsDto;
import ru.got.shop.mapper.AdsMapperImpl;
import ru.got.shop.mapper.FullAdsMapperImpl;
import ru.got.shop.mapper.PictureMapperImpl;
import ru.got.shop.model.Ads;
import ru.got.shop.model.Picture;
import ru.got.shop.model.User;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.PictureRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.impl.AdsServiceImpl;
import ru.got.shop.service.impl.PictureDiskServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.got.shop.controller.UserControllerFactory.USER_LOGIN;

//@WebMvcTest(controllers = AdsController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class AdsControllerTest {
    private static final String AD_ID = "/ads/{id}";
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private AdsMapperImpl adsMapper;
    @SpyBean
    private FullAdsMapperImpl fullAdsMapper;
    @SpyBean
    private PictureMapperImpl pictureMapper;
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
    @InjectMocks
    private AdsController adsController;
    private AdsFactory adsFactory;
    private final String ADS = "/ads";
    private final String ADS_ME = "/ads/me";
    @Autowired
    private ObjectMapper mapper;

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
    void getAllAds_Status_200() throws Exception {
        json = mapper.writeValueAsString(new ResponseWrapperAdsDto(1, List.of(adDto)));
        when(adsRepository.findAll()).thenReturn(List.of(ads));
        mockMvc.perform(get(ADS).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }



    //    @Test
//    @WithMockUser(username = USER_LOGIN, authorities = "users.full")
//    void addAd_Status_200() throws Exception {
//        json = mapper.writeValueAsString(adDto);
//        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));
//        when(adsRepository.save(ads)).thenReturn(ads);
//        when(pictureRepository.save(picture)).thenReturn(picture);
//        mockMvc.perform(post(ADS_URI).contentType(MediaType.MULTIPART_FORM_DATA)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json)
//                .content(json.getBytes(StandardCharsets.UTF_8))).andDo(print()).andExpect(status().isOk());
//    }
    //
//    @Test
//    void editPicture() {
//    }
//
    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void getAdImage() throws Exception {
        String filePath = "src/test/resources/user_avatar_big.jpg";
        byte[] data = Files.readAllBytes(Path.of(filePath));
        picture.setFilePath(filePath);
        when(pictureRepository.findByUuid(UUID.randomUUID())).thenReturn(Optional.of(picture));
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(pictureDiskServiceImpl.upload(any(UUID.class))).thenReturn(data);
        String adsImg = "/ads/image/eeaffff0-c242-41cb-9baf-44ed689c2c25";
        mockMvc.perform(get(adsImg).accept(MediaType.IMAGE_PNG))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(data));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void getMyAds() throws Exception {
        when(adsRepository.findAllByUserId(user)).thenReturn(List.of(ads));
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));
        json = mapper.writeValueAsString(new ResponseWrapperAdsDto(1, List.of(adDto)));
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
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_LOGIN, authorities = "ads.crud")
    void getFullAd() throws Exception {
        when(adsRepository.findById(1)).thenReturn(Optional.ofNullable(ads));
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));
        FullAdDto fullAdDto = fullAdsMapper.toDto(user, ads);
        String json = mapper.writeValueAsString(fullAdDto);
        mockMvc.perform(get(AD_ID, 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @WithMockUser(username = USER_LOGIN, roles = {})
    void removeAd_203() throws Exception {
        when(adsRepository.findById(1)).thenReturn(Optional.ofNullable(ads));
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));
        mockMvc.perform(delete(AD_ID,1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
//
//    @Test
//    void updateAd() {
//    }
}