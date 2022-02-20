package com.hepsiburada.ecommerce.dto.output;

import lombok.Data;

@Data
public abstract class BaseOutput {
    private boolean isSuccess;
    private String message;

    public abstract String getReturnMessage();
}
