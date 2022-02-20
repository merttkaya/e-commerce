package com.hepsiburada.ecommerce.dto.output;

import com.hepsiburada.ecommerce.model.Campaign;
import lombok.Data;

@Data
public class GetCampaignInfoByIdOutputDto extends BaseOutput{
    private Campaign campaign;

    @Override
    public String getReturnMessage() {
        if(isSuccess()){
            String outMessage = "Campaign " + campaign.getName() + " info; Status " + (campaign.getIsActive() ? "Active " : "Ended ");
            outMessage = outMessage + "Target Sales " + campaign.getTargetSalesCount() + ", " + campaign.getTotalSales() + ", Turnover 0, Average Item Price " + campaign.getAverageItemPrice();
            return outMessage;
        }
        else
            return getMessage();
    }
}
