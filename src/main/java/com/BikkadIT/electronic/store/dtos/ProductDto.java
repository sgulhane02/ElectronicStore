package com.BikkadIT.electronic.store.dtos;

import com.BikkadIT.electronic.store.validate.ImageNameValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productId;

    @NotEmpty
    @Size(min = 4,message = "Title is required")
    private String title;

    @NotEmpty
    @Size(min=10,message = "Description is required")
    private String description;

    @NotEmpty
    private Double price;

    @NotEmpty
    private Double discountedPrice;

    @NotEmpty
    private Integer quantity;

    @NotEmpty
    private Date addedDate;

    @NotEmpty
    private Boolean live;

    @NotEmpty
    private Boolean stock;

    @ImageNameValid
    private String imageName;
}
