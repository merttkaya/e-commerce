package com.hepsiburada.ecommerce.dto.input;

import lombok.Data;

@Data
public class CreateCampaignInputDto {
    private String name;
    private String productCode;
    private Long duration;
    private Long pmLimit;
    private Long targetSalesCount;
}
