package com.hepsiburada.ecommerce.dto.output;

import com.hepsiburada.ecommerce.model.Product;
import lombok.Data;

@Data
public class CreateProductOutputDto extends BaseOutput{
    private Product product;

    @Override
    public String getReturnMessage() {
        if(isSuccess())
            return "Product created; code " + product.getProductCode() + ", price " + product.getPrice() + ", stock " + product.getRemainingStock();
        else
            return getMessage();
    }
}
