package com.hepsiburada.ecommerce.dto.output;

import com.hepsiburada.ecommerce.model.Product;
import lombok.Data;

@Data
public class GetProductInfoOutputDto extends BaseOutput{
    private Product product;

    @Override
    public String getReturnMessage() {
        if(isSuccess())
            return "Product " + product.getProductCode() + " info; price " + product.getPrice() + " stock " + product.getRemainingStock();
        else
            return getMessage();

    }
}
