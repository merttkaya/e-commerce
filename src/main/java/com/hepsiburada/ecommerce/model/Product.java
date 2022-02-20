package com.hepsiburada.ecommerce.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
@RequiredArgsConstructor
public class Product {

    @Id
    private String productCode;

    @Column
    private Long price;

    @Column
    private Long stock;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, mappedBy="product")
    private Set<Campaign> campaigns;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, mappedBy="product")
    private Set<ProductOrder> productOrders;

    public Long getRemainingStock(){
        if(this.getProductOrders() != null)
            return this.getStock() - this.getProductOrders().stream().mapToLong(ProductOrder::getQuantity).sum();
        return this.getStock();
    }

}
