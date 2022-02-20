package com.hepsiburada.ecommerce.service.campaign;

import com.hepsiburada.ecommerce.dto.input.GetCampaignInfoByIdInputDto;
import com.hepsiburada.ecommerce.dto.output.GetCampaignInfoByIdOutputDto;
import com.hepsiburada.ecommerce.exception.CampaignNotFoundException;
import com.hepsiburada.ecommerce.repository.CampaignRepository;
import com.hepsiburada.ecommerce.service.ServiceAbstract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@RequiredArgsConstructor
public class GetCampaignInfoService extends ServiceAbstract {

    private final CampaignRepository campaignRepository;

    public GetCampaignInfoByIdOutputDto getCampaignInfoById(GetCampaignInfoByIdInputDto inputDto) {
        GetCampaignInfoByIdOutputDto outputDto = new GetCampaignInfoByIdOutputDto();
        try {
            outputDto.setCampaign(campaignRepository.findById(inputDto.getCampaignName()).orElseThrow(CampaignNotFoundException::new));
            outputDto.setSuccess(true);
        } catch (CampaignNotFoundException campaignNotFoundException){
            outputDto.setSuccess(false);
            outputDto.setMessage("ERROR - Campaign: " + inputDto.getCampaignName() + " is not found");
            logger.log(Level.WARNING,outputDto.getMessage());
        }
        return outputDto;
    }

}
