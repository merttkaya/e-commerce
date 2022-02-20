package com.hepsiburada.ecommerce.dto.output;

import com.hepsiburada.ecommerce.model.ProductOrder;
import lombok.Data;

@Data
public class CreateProductOrderOutputDto extends BaseOutput{
    private ProductOrder productOrder;

    @Override
    public String getReturnMessage() {
        if(isSuccess())
            return "Order created; product " + productOrder.getProduct().getProductCode() + ", quantity " + productOrder.getQuantity();
        else
            return getMessage();
    }
}
