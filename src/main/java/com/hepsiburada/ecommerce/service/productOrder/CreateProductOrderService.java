package com.hepsiburada.ecommerce.service.productOrder;

import com.hepsiburada.ecommerce.dto.input.CreateProductOrderInputDto;
import com.hepsiburada.ecommerce.dto.input.GetCampaignInfoByProductIdInputDto;
import com.hepsiburada.ecommerce.dto.output.CreateProductOrderOutputDto;
import com.hepsiburada.ecommerce.dto.output.GetCampaignInfoByProductIdOutputDto;
import com.hepsiburada.ecommerce.exception.ProductNotFoundException;
import com.hepsiburada.ecommerce.exception.ProductOutOfStockException;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.model.ProductOrder;
import com.hepsiburada.ecommerce.repository.ProductOrderRepository;
import com.hepsiburada.ecommerce.repository.ProductRepository;
import com.hepsiburada.ecommerce.service.ServiceAbstract;
import com.hepsiburada.ecommerce.service.campaign.GetCampaignInfoByProductIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@RequiredArgsConstructor
public class CreateProductOrderService extends ServiceAbstract {

    private final ProductRepository productRepository;

    private final ProductOrderRepository productOrderRepository;

    private final GetCampaignInfoByProductIdService getCampaignInfoByProductIdService;

    public CreateProductOrderOutputDto createProductOrder(CreateProductOrderInputDto inputDto) {
        CreateProductOrderOutputDto outputDto = new CreateProductOrderOutputDto();
        try{
            Product p = productRepository.findByProductCode(inputDto.getProductCode()).orElse(null);
            if(p == null){
                outputDto.setSuccess(false);
                outputDto.setMessage("ERROR - Product: " + inputDto.getProductCode() + " not found");
                throw new ProductNotFoundException();
            }

            Long productStock = p.getRemainingStock();
            if(productStock < inputDto.getQuantity()){
                outputDto.setSuccess(false);
                outputDto.setMessage("ERROR - Product: " + inputDto.getProductCode() + " has no stock amount of: " +inputDto.getQuantity());
                throw new ProductOutOfStockException();
            }

            GetCampaignInfoByProductIdInputDto infoByProductIdInputDto = new GetCampaignInfoByProductIdInputDto();
            infoByProductIdInputDto.setProduct(p);
            GetCampaignInfoByProductIdOutputDto campaign = getCampaignInfoByProductIdService.getCampaignInfoByProductId(infoByProductIdInputDto);

            Long productPrize = p.getPrice();
            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(p);
            productOrder.setQuantity(inputDto.getQuantity());
            if(campaign.isSuccess() && campaign.getCampaign() != null){
                productOrder.setCampaign(campaign.getCampaign());
                productPrize = productPrize - productPrize * campaign.getCampaign().getCurrentDiscountRate() / 100;
            }
            productOrder.setProductPrize(productPrize);
            productOrderRepository.save(productOrder);

            outputDto.setSuccess(true);
            outputDto.setProductOrder(productOrder);
            return outputDto;
        } catch (ProductNotFoundException | ProductOutOfStockException productNotFoundException){
            logger.log(Level.WARNING, outputDto.getReturnMessage());
        }
        return outputDto;

    }
}
