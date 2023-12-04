package com.BikkadIT.electronic.store.controller;

import com.BikkadIT.electronic.store.constants.AppConstant;
import com.BikkadIT.electronic.store.constants.UrlConstant;
import com.BikkadIT.electronic.store.dtos.UserDto;
import com.BikkadIT.electronic.store.payload.ApiResponseMessage;
import com.BikkadIT.electronic.store.payload.ImageResponse;
import com.BikkadIT.electronic.store.payload.PageableResponse;
import com.BikkadIT.electronic.store.services.ImageService;
import com.BikkadIT.electronic.store.services.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping(UrlConstant.BASE_URL+UrlConstant.USERS_URL)
public class UserController {
    @Autowired
	private UserService userService;
    @Autowired
    private ImageService imageService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @auther Shrikant
     * @param userDto
     * @return
     * @since 1.0v
     */
	//create
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        log.info("Entering request for adding user");
    	UserDto userDto1 = userService.saveUser(userDto);
        log.info("Completed request of user data uploaded");
		return new ResponseEntity<>(userDto1, HttpStatus.CREATED );
    }
    //update

    /**
     * @auther Shrikant
     * @param userId
     * @param userDto
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> upadateUser(
    		@PathVariable String userId,
    		@Valid @RequestBody UserDto userDto)
    {
        log.info("Entering request for update user with userId : {}",userId);
    	UserDto UserDto1 = userService.updateUser(userDto, userId);
        log.info("Complete request for update user with userId : {} ", userId);
    	return new ResponseEntity<>(UserDto1, HttpStatus.OK);
    }
    //delete

    /**
     * @auther Shrikant
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        log.info("Entering request for delete user");
        this.userService.deleteUser(userId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(AppConstant.DELETED + userId).status(false).httpStatus(HttpStatus.OK).build();
        log.info("Complete request for delete user with id");
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }



    //get all

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
    		@RequestParam(value = "pageNumber", defaultValue = UrlConstant.PAGE_NUMBER, required = false) int pageNumber,
    		@RequestParam(value = "pageSize", defaultValue = UrlConstant.PAGE_SIZE, required = false) int pageSize,
    		@RequestParam(value = "sortBy", defaultValue = UrlConstant.SORT_BY, required = false) String sortBy,
    		@RequestParam(value = "sortDir", defaultValue = UrlConstant.SORT_DIRECTION , required = false) String sortDir
    		){

       log.info("Entering request for get all user records");
        PageableResponse<UserDto> allUsers = userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed request for get all user records");
        return new ResponseEntity<>(allUsers,HttpStatus.OK);

    }
    
    //get single user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
    	return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK); 
    }
    
    //get by email
    /**
     * @author Shrikant
     * @param email
     * @return
     * @apiNote get user by user Email
     * @since 1.0v
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email ){
    	return new ResponseEntity<>(userService.getuserByEmail(email), HttpStatus.OK); 
    }
    
  //Search user

    /**
     *
     * @param keywords
     * @return
     */
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords){
    	return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK); 
    }

    /**
     *
     * @param image
     * @param userId
     * @return
     * @throws IOException
     */
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam MultipartFile image,@PathVariable String userId) throws IOException {


        String imageName = imageService.uploadFile(image, imageUploadPath);
        log.info("Entering request for posting image");
        UserDto user = userService.getUserById(userId);
        user.setImage(imageName);
        userService.updateUser(user,userId);
        log.info("Completed request for posting image in database");
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    /**
     *
     * @param userId
     * @param response
     * @throws IOException
     */
    @GetMapping("/image/{userId}")
    public void downloadImage(@PathVariable String userId , HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        log.info("Entering request for getting uploaded image");
        InputStream resource = imageService.getResource(imageUploadPath, user.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Completed request for getting uploaded image with userId : {}",userId);
        StreamUtils.copy(resource,response.getOutputStream());

    }

}
