package com.hepsiburada.ecommerce.service;

import com.hepsiburada.ecommerce.dto.input.IncreaseTimeInputDto;
import com.hepsiburada.ecommerce.dto.output.IncreaseTimeOutputDto;
import com.hepsiburada.ecommerce.service.campaign.UpdateCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IncreaseTimeService extends ServiceAbstract {

    private final UpdateCampaignService updateCampaignService;
    public static LocalDateTime baseSystemDate = LocalDate.now().atTime(0, 0, 0, 0);
    public static LocalDateTime systemDate = LocalDate.now().atTime(0, 0, 0, 0);

    public IncreaseTimeOutputDto increaseTime(IncreaseTimeInputDto inputDto) {
        systemDate = systemDate.plusHours(inputDto.getIncrement());
        updateCampaignService.updateCampaigns();
        IncreaseTimeOutputDto outputDto = new IncreaseTimeOutputDto();
        outputDto.setMessage("Time is " + getHourString());
        return outputDto;
    }

    private String getHourString() {
        String time = systemDate.getHour() < 10 ? "0" + systemDate.getHour() : Integer.toString(systemDate.getHour());
        time += ":";
        time += systemDate.getMinute() < 10 ? "0" + systemDate.getMinute() : systemDate.getMinute();

        return time;
    }
}
