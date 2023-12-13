package com.BikkadIT.electronic.store.services;

import com.BikkadIT.electronic.store.dtos.UserDto;
import com.BikkadIT.electronic.store.entities.User;
import com.BikkadIT.electronic.store.payload.PageableResponse;
import com.BikkadIT.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

        @MockBean
        private UserRepository userRepository;


        @Autowired
        private UserService userService;

        @Autowired
        private ModelMapper modelMapper;

        User user;

        @BeforeEach
        public void init(){
            user = User.builder()
                    .name("John Warner")
                    .email("john.warner@example.com")
                    .gender("male")
                    .about("Software Engineer")
                    .image("profile_pic.jpg")
                    .password("password")
                    .build();
        }

        @Test
        public void createUser() {

            Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
            UserDto user1 = userService.saveUser(modelMapper.map(user, UserDto.class));
            System.out.println(user1.getName());
            Assertions.assertNotNull(user1);
            Assertions.assertEquals("John Warner",user1.getName());

        }

        @Test
        public void updateUserTest(){
            String userId="";

            UserDto userDto = UserDto.builder()
                    .name("Jane Doe")
                    .email("jane.doe@example.com")
                    .gender("female")
                    .about("Senior Software Engineer")
                    .image("new_profile_pic.jpg")
                    .password("new_secure_password")
                    .build();

            Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
            Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

            UserDto updateUser = userService.updateUser(userDto, userId);
            System.out.println(updateUser.getName());
            Assertions.assertNotNull(userDto);
        }

        @Test
        public void deleteUserTest(){

            String userId="Rahul";

            Mockito.when(userRepository.findById("Rahul")).thenReturn(Optional.of(user));
            userService.deleteUser(userId);

        }
        @Test
        public void getAllUserTest(){

            User  user1 = User.builder()
                    .name("Ramesh Mahale")
                    .email("ramesh.mahale@example.com")
                    .gender("other")
                    .about("Developer")
                    .image("updated_profile_pic.jpg")
                    .password("secure_password")
                    .build();

            User user2 = User.builder()
                    .name("John Doe")
                    .email("john.doe@example.com")
                    .gender("male")
                    .about("Senior Software Engineer")
                    .image("new_profile_pic.jpg")
                    .password("new_and_secure_password")
                    .build();

            List<User> userList = Arrays.asList(user,user1,user2);
            Page<User> page=new PageImpl<>(userList);
            Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

            PageableResponse<UserDto> allUsers = userService.getAllUsers(1,2,"name","asc");
            Assertions.assertEquals(3,allUsers.getContent().size());

        }
        @Test
        public void getUserByIdTest(){

            String userId="userIdabcd";
            Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

            UserDto userDto = userService.getUserById(userId);
            Assertions.assertNotNull(userDto);
            Assertions.assertEquals(user.getName(),userDto.getName(),"name not matched");
        }
        @Test
        public void getUserByEmailTest(){

            String emailId="pb@gmail.com";
            Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));

            UserDto userDto = userService.getuserByEmail(emailId);
            Assertions.assertNotNull(userDto);
            Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"email not matched!!");
        }

        @Test
        public void searchUserTest(){
            User user = User.builder()
                    .name("Ramesh Patil")
                    .email("ramesh.patil@example.com")
                    .gender("male")
                    .about("Senior Software Engineer")
                    .image("new_profile_pic.jpg")
                    .password("secure_password")
                    .build();

            User user1 = User.builder()
                    .name("Abhijeet Dahake")
                    .email("abhijeetdahake@example.com")
                    .gender("male")
                    .about("Senior Software Engineer")
                    .image("new_profile_pic.jpg")
                    .password("secure_password")
                    .build();

            User user2 = User.builder()
                    .name("Andrew Tate")
                    .email("andrewt@example.com")
                    .gender("male")
                    .about("Senior Software Engineer")
                    .image("new_profile_pic.jpg")
                    .password("new_and_secure_password")
                    .build();

            String keyword="Abhijeet";
            Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(Arrays.asList(user,user1,user2));
            List<UserDto> userDtos = userService.searchUser(keyword);
            Assertions.assertEquals(3,userDtos.size(),"size not matched");
        }

    }
