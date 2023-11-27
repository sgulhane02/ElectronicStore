package com.BikkadIT.electronic.store.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {
    private String message;

    private boolean status;

    private HttpStatus httpStatus;

}
