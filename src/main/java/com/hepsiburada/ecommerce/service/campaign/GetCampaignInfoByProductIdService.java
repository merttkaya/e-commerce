package com.hepsiburada.ecommerce.service.campaign;

import com.hepsiburada.ecommerce.dto.input.GetCampaignInfoByProductIdInputDto;
import com.hepsiburada.ecommerce.dto.output.GetCampaignInfoByProductIdOutputDto;
import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.repository.CampaignRepository;
import com.hepsiburada.ecommerce.service.ServiceAbstract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetCampaignInfoByProductIdService extends ServiceAbstract {

    private final CampaignRepository campaignRepository;

    public GetCampaignInfoByProductIdOutputDto getCampaignInfoByProductId(GetCampaignInfoByProductIdInputDto campaignInputDto) {
        GetCampaignInfoByProductIdOutputDto outputDto = new GetCampaignInfoByProductIdOutputDto();
        Set<Campaign> campaigns = campaignRepository.getCampaignByProductAndIsActiveOrderByStartDateDesc(campaignInputDto.getProduct(), true);

        outputDto.setCampaign(campaigns.stream().findFirst().orElse(null));
        outputDto.setSuccess(true);
        return outputDto;
    }


}
