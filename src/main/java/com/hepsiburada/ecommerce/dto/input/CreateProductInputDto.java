package com.hepsiburada.ecommerce.dto.input;

import lombok.Data;

@Data
public class CreateProductInputDto {
    private String productCode;
    private Long price;
    private Long stock;
}
