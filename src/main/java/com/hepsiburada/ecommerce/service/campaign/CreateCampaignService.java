package com.hepsiburada.ecommerce.service.campaign;

import com.hepsiburada.ecommerce.dto.input.CreateCampaignInputDto;
import com.hepsiburada.ecommerce.dto.output.CreateCampaignOutputDto;
import com.hepsiburada.ecommerce.exception.ProductNotFoundException;
import com.hepsiburada.ecommerce.exception.SameCampaignExistException;
import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.CampaignRepository;
import com.hepsiburada.ecommerce.repository.ProductRepository;
import com.hepsiburada.ecommerce.service.IncreaseTimeService;
import com.hepsiburada.ecommerce.service.ServiceAbstract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@RequiredArgsConstructor
public class CreateCampaignService extends ServiceAbstract {

    private final CampaignRepository campaignRepository;

    private final ProductRepository productRepository;

    public CreateCampaignOutputDto createCampaign(CreateCampaignInputDto inputDto) {
        CreateCampaignOutputDto outputDto = new CreateCampaignOutputDto();
        try {
            if(campaignRepository.existsById(inputDto.getName())) {
                outputDto.setSuccess(false);
                outputDto.setMessage("ERROR - Campaign: " + inputDto.getName() + " is already exist");
                throw new SameCampaignExistException();
            }

            Product p = productRepository.findByProductCode(inputDto.getProductCode()).orElse(null);
            if(p == null) {
                outputDto.setSuccess(false);
                outputDto.setMessage("ERROR - Product: " + inputDto.getProductCode() + " not found");
                throw new ProductNotFoundException();
            }
            Campaign campaign = new Campaign();
            campaign.setName(inputDto.getName());
            campaign.setDuration(inputDto.getDuration());
            campaign.setPriceManipulationLimit(inputDto.getPmLimit());
            campaign.setTargetSalesCount(inputDto.getTargetSalesCount());
            campaign.setProduct(p);
            campaign.setStartDate(IncreaseTimeService.systemDate);
            campaign.setEndDate(campaign.getStartDate().plusHours(inputDto.getDuration()));
            campaign.setCurrentDiscountRate(inputDto.getPmLimit() * 10 / 100);
            campaignRepository.save(campaign);
            outputDto.setSuccess(true);
            outputDto.setCampaign(campaign);
        } catch (SameCampaignExistException | ProductNotFoundException exception){
            logger.log(Level.WARNING, outputDto.getMessage());
        }

        return outputDto;
    }
}
