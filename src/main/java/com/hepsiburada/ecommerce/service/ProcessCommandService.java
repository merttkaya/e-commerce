package com.hepsiburada.ecommerce.service;

import com.hepsiburada.ecommerce.dto.input.*;
import com.hepsiburada.ecommerce.dto.output.BaseOutput;
import com.hepsiburada.ecommerce.service.campaign.CreateCampaignService;
import com.hepsiburada.ecommerce.service.campaign.GetCampaignInfoService;
import com.hepsiburada.ecommerce.service.product.CreateProductService;
import com.hepsiburada.ecommerce.service.product.GetProductInfoService;
import com.hepsiburada.ecommerce.service.productOrder.CreateProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessCommandService extends ServiceAbstract {

    private final CreateProductService createProductService;

    private final GetProductInfoService getProductInfoService;

    private final CreateProductOrderService createProductOrderService;

    private final CreateCampaignService createCampaignService;

    private final GetCampaignInfoService getCampaignInfoService;

    private final IncreaseTimeService increaseTimeService;

    public BaseOutput callCreateCampaignService(String[] parameters) throws NumberFormatException {
        CreateCampaignInputDto inputDto = new CreateCampaignInputDto();
        inputDto.setName(parameters[1]);
        inputDto.setProductCode(parameters[2]);
        inputDto.setDuration(Long.parseLong(parameters[3]));
        inputDto.setPmLimit(Long.parseLong(parameters[4]));
        inputDto.setTargetSalesCount(Long.parseLong(parameters[5]));
        return createCampaignService.createCampaign(inputDto);
    }

    public BaseOutput callGetCampaignService(String[] parameters) {
        GetCampaignInfoByIdInputDto inputDto = new GetCampaignInfoByIdInputDto();
        inputDto.setCampaignName(parameters[1]);
        return getCampaignInfoService.getCampaignInfoById(inputDto);
    }

    public BaseOutput callGetProductInfoService(String[] parameters) {
        GetProductInfoInputDto inputDto = new GetProductInfoInputDto();
        inputDto.setProductCode(parameters[1]);
        return getProductInfoService.getProductInfo(inputDto);
    }

    public BaseOutput callCreateProductService(String[] parameters) throws NumberFormatException {
        CreateProductInputDto inputDto = new CreateProductInputDto();
        inputDto.setProductCode(parameters[1]);
        inputDto.setPrice(Long.parseLong(parameters[2]));
        inputDto.setStock(Long.parseLong(parameters[3]));
        return createProductService.createProduct(inputDto);
    }

    public BaseOutput callCreateProductOrderService(String[] parameters) throws NumberFormatException {
        CreateProductOrderInputDto inputDto = new CreateProductOrderInputDto();
        inputDto.setProductCode(parameters[1]);
        inputDto.setQuantity(Long.parseLong(parameters[2]));
        return createProductOrderService.createProductOrder(inputDto);
    }

    public BaseOutput callIncreaseTimeService(String[] parameters) throws NumberFormatException {
        IncreaseTimeInputDto inputDto = new IncreaseTimeInputDto();
        inputDto.setIncrement(Integer.parseInt(parameters[1]));
        return increaseTimeService.increaseTime(inputDto);
    }
}
