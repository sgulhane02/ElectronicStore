package com.BikkadIT.electronic.store.controllers;

import com.BikkadIT.electronic.store.dtos.UserDto;
import com.BikkadIT.electronic.store.entities.User;
import com.BikkadIT.electronic.store.payload.PageableResponse;
import com.BikkadIT.electronic.store.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    private User user;


    @BeforeEach
    public void init(){
        user = User.builder()
                .name("Shrikant")
                .email("sg@gmail.com")
                .gender("male")
                .about("java developer")
                .image("abc.png")
                .password("Password@123")
                .build();
    }

    @Test
    public void createUserTest() throws Exception {

        UserDto dto = modelMapper.map(user,UserDto.class);
        Mockito.when(userService.saveUser(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").exists());

    }

    private String convertObjectToJsonString(Object user) throws JsonProcessingException {

        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Test
    public void updateUserTest() throws Exception {

        String userId="userAbc";
        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/users/"+userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").exists());
    }

    @Test
    public void getAllUserTest() throws Exception {

        UserDto userDto = UserDto.builder().name("Manish").email("mani@gmail.com").about("Data Scientist").gender("male").password("pass").image("mani.png").build();
        UserDto userDto1 = UserDto.builder().name("Trisha").email("Tr@gmail.com").about("Software tester").gender("female").password("pass").image("trisha.png").build();
        UserDto userDto2 = UserDto.builder().name("Harish").email("hari@gmail.com").about("Java Developer").gender("male").password("hari").image("hari.png").build();
        PageableResponse<UserDto> pagResponse=new PageableResponse<>();

        pagResponse.setContent(Arrays.asList(userDto,userDto1,userDto2));
        pagResponse.setLastPage(false);
        pagResponse.setPageNumber(100);
        pagResponse.setPageSize(10);
        pagResponse.setTotalElements(1000);
        Mockito.when(userService.getAllUsers(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pagResponse);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void getUserByIdTest() throws Exception {

        String userId="userabcd";
        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/" + userId))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void deleteUserTest() throws Exception {

        String userId="userabcd";
        Mockito.doNothing().when(userService).deleteUser(userId);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/" + userId))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void getUserByEmailTest() throws Exception {

        String email="hari@gmail.com";
        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.getuserByEmail(Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/email/"+email))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void searchUserTest() throws Exception {

        UserDto user1 = UserDto.builder()
                .name("Abhijeet")
                .email("abhia@gmail.com")
                .gender("male")
                .about("java developer")
                .image("abc.png")
                .password("abc")
                .build();

        UserDto user2 = UserDto.builder()
                .name("Aman")
                .email("aman@gmail.com")
                .gender("male")
                .about("java developer")
                .image("abc.png")
                .password("abc")
                .build();

        String keyword="aman";

        Mockito.when(userService.searchUser(Mockito.anyString())).thenReturn(Arrays.asList(user1,user2));

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/search/"+keyword))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

