package ru.got.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.got.shop.dto.LoginReqDto;
import ru.got.shop.dto.RegReqDto;
import ru.got.shop.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserRepository userRepository;

    @Test
    void loginUsingPOST() throws Exception {
        String path = "/login";

        LoginReqDto loginReqDto = LoginReqDto.builder().username(UserControllerFactory.USER_LOGIN).password("userpass").build();
        String json = objectMapper.writeValueAsString(loginReqDto);

        Mockito.when(userRepository.findFirstByEmail(UserControllerFactory.USER_LOGIN)).thenReturn(Optional.of(UserControllerFactory.getUserEntity()));

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void loginUsingPOST_403() throws Exception {
        String path = "/login";

        LoginReqDto loginReqDto = LoginReqDto.builder().username(UserControllerFactory.USER_LOGIN).password("WRONG_PASS").build();
        String json = objectMapper.writeValueAsString(loginReqDto);

        Mockito.when(userRepository.findFirstByEmail(UserControllerFactory.USER_LOGIN)).thenReturn(Optional.of(UserControllerFactory.getUserEntity()));

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    void registerUsingPOST() throws Exception {
        //RegReqDto
        String path = "/register";
        Mockito.when(userRepository.findFirstByEmail(any(String.class))).thenReturn(Optional.empty());

        RegReqDto regReqDto = RegReqDto.builder()
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .username("user@name.ru")
                .phone("+799911224455")
                .password("password")
                .build();

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regReqDto))
                ).andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    void registerUsingPOST_400_user_exists() throws Exception {
        //RegReqDto
        String path = "/register";
        Mockito.when(userRepository.findFirstByEmail(any(String.class))).thenReturn(Optional.of(UserControllerFactory.getUserEntity()));

        RegReqDto regReqDto = RegReqDto.builder()
                .username("user@name.ru")
                .build();

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regReqDto))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"This email is already in use another account!!!\""));

    }


}