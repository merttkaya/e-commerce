package com.hepsiburada.ecommerce.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
@RequiredArgsConstructor
public class Campaign {

    @Id
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "productCode", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long duration;

    @Column(nullable = false)
    private Long priceManipulationLimit;

    @Column(nullable = false)
    private Long targetSalesCount;

    @Column
    private Boolean isActive = true;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "campaign")
    private Set<ProductOrder> productOrders;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private Long currentDiscountRate = 0L;

    public Long getTotalSales() {
        if(getProductOrders() != null) {
            return getProductOrders()
                    .stream()
                    .mapToLong(ProductOrder::getQuantity)
                    .sum();
        }
        return 0L;
    }

    public String getAverageItemPrice() {
        if(getProductOrders() != null && getTotalSales() != 0){
            Long totalPrice = getProductOrders()
                    .stream()
                    .mapToLong(po -> po.getQuantity() * po.getProductPrize())
                    .sum();
            Long average = totalPrice / getTotalSales();
            return average.toString();
        }

        return "-";

    }
}
