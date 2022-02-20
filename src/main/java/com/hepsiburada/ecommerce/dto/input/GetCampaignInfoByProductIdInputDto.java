package com.hepsiburada.ecommerce.dto.input;

import com.hepsiburada.ecommerce.model.Product;
import lombok.Data;

@Data
public class GetCampaignInfoByProductIdInputDto {
    private Product product;
}
