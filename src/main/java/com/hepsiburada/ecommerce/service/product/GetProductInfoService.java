package com.hepsiburada.ecommerce.service.product;

import com.hepsiburada.ecommerce.dto.input.GetCampaignInfoByProductIdInputDto;
import com.hepsiburada.ecommerce.dto.input.GetProductInfoInputDto;
import com.hepsiburada.ecommerce.dto.output.GetCampaignInfoByProductIdOutputDto;
import com.hepsiburada.ecommerce.dto.output.GetProductInfoOutputDto;
import com.hepsiburada.ecommerce.exception.ProductNotFoundException;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.ProductRepository;
import com.hepsiburada.ecommerce.service.ServiceAbstract;
import com.hepsiburada.ecommerce.service.campaign.GetCampaignInfoByProductIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;

@Service
@RequiredArgsConstructor
public class GetProductInfoService extends ServiceAbstract {

    private final ProductRepository productRepository;

    private final GetCampaignInfoByProductIdService getCampaignInfoByProductIdService;

    public GetProductInfoOutputDto getProductInfo(GetProductInfoInputDto inputDto) {
        GetProductInfoOutputDto outputDto = new GetProductInfoOutputDto();

        try {
            Optional<Product> product = productRepository.findByProductCode(inputDto.getProductCode());
            if (product.isPresent()) {
                Product p = product.get();
                GetCampaignInfoByProductIdInputDto infoByProductIdInputDto = new GetCampaignInfoByProductIdInputDto();
                infoByProductIdInputDto.setProduct(p);
                GetCampaignInfoByProductIdOutputDto campaign = getCampaignInfoByProductIdService.getCampaignInfoByProductId(infoByProductIdInputDto);
                if (campaign.getCampaign() != null)
                    p.setPrice(p.getPrice() - (p.getPrice() * campaign.getCampaign().getCurrentDiscountRate() / 100));
                outputDto.setProduct(p);
                outputDto.setSuccess(true);
            } else{
                throw new ProductNotFoundException();
            }
        } catch (ProductNotFoundException productNotFoundException){
            outputDto.setSuccess(false);
            outputDto.setMessage("ERROR - Product: " + inputDto.getProductCode() + " not found");
            logger.log(Level.WARNING, outputDto.getMessage());
        }
        return outputDto;
    }
}
