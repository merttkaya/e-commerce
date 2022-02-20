package com.hepsiburada.ecommerce.service.campaign;

import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.model.ProductOrder;
import com.hepsiburada.ecommerce.repository.CampaignRepository;
import com.hepsiburada.ecommerce.service.IncreaseTimeService;
import com.hepsiburada.ecommerce.service.ServiceAbstract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateCampaignService extends ServiceAbstract {
    private final CampaignRepository campaignRepository;

    //This function calls when time passes. It updates every campaign state or discount rate.
    public void updateCampaigns() {
        LocalDateTime currentTime = IncreaseTimeService.systemDate;
        Iterable<Campaign> campaigns = campaignRepository.getAllByIsActiveIsTrue();
        campaigns.forEach(campaign -> {
            if (campaign.getEndDate().isEqual(currentTime) || campaign.getEndDate().isBefore(currentTime)) {
                campaign.setIsActive(false);
            } else {
                Long totalSales = campaign.getProductOrders()
                        .stream()
                        .mapToLong(ProductOrder::getQuantity)
                        .sum();
                double saleRate = (double) totalSales / campaign.getTargetSalesCount();
                Long newDiscountRate;
                if (saleRate > 0.74)        //Price discount percentage decreases when orders increase
                    newDiscountRate = campaign.getPriceManipulationLimit() * 35 / 100;
                else if (saleRate > 0.49)
                    newDiscountRate = campaign.getPriceManipulationLimit() * 50 / 100;
                else if (saleRate > 0.24)
                    newDiscountRate = campaign.getPriceManipulationLimit() * 65 / 100;
                else
                    newDiscountRate = campaign.getPriceManipulationLimit();

                campaign.setCurrentDiscountRate(newDiscountRate);
            }
        });

        campaignRepository.saveAll(campaigns);
    }

}
