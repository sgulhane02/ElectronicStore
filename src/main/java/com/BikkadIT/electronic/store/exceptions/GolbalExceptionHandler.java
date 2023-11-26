package com.BikkadIT.electronic.store.exceptions;
import com.BikkadIT.electronic.store.payload.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GolbalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundHandler(ResourceNotFound re) {

        ApiResponseMessage apiResponse = ApiResponseMessage.builder().message(re.message).status(false).httpStatus(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<ApiResponseMessage>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotvalidExceptionHandler(MethodArgumentNotValidException me){

        Map<String,String> map=new HashMap<>();

        me.getBindingResult().getFieldErrors().forEach(e -> map.put(e.getField(),e.getDefaultMessage()));

        return new ResponseEntity<Map<String, String>>(map,HttpStatus.NOT_FOUND);

    }


}
