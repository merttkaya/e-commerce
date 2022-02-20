package com.hepsiburada.ecommerce.dto.input;

import lombok.Data;

@Data
public class CreateProductOrderInputDto {
    private String productCode;
    private Long quantity;
}
