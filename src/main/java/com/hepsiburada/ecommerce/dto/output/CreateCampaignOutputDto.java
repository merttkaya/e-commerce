package com.hepsiburada.ecommerce.dto.output;

import com.hepsiburada.ecommerce.model.Campaign;
import lombok.Data;

@Data
public class CreateCampaignOutputDto extends BaseOutput{
    private Campaign campaign;

    @Override
    public String getReturnMessage() {
        if(isSuccess()){
            String outMessage = "Campaign created; name " + campaign.getName() + ", product " + campaign.getProduct().getProductCode();
            outMessage = outMessage + ", duration " + campaign.getDuration() + ", limit " + campaign.getPriceManipulationLimit() + ", target sales count " + campaign.getTargetSalesCount();
            return outMessage;
        } else
            return getMessage();
    }
}
