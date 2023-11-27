package com.BikkadIT.electronic.store.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy=ImageNameValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageNameValid {

    //Error Message
    String message() default "Invalid Image Name!!";

    //Represent Group Of Constraints
    Class<?>[] groups() default { };

    //Additional Information About Annotations
    Class<? extends Payload>[] payload() default { };
}
