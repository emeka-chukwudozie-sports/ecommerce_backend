package com.saha.e_commerce.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDto {

    private Integer id;
    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;
}
