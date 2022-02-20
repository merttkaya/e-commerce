package com.hepsiburada.ecommerce.repository;

import com.hepsiburada.ecommerce.model.Campaign;
import com.hepsiburada.ecommerce.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CampaignRepository extends CrudRepository<Campaign, String> {
    Iterable<Campaign> getAllByIsActiveIsTrue();

    Set<Campaign> getCampaignByProductAndIsActiveOrderByStartDateDesc(Product product, Boolean isActive);
}
