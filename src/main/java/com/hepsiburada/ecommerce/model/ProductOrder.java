package com.hepsiburada.ecommerce.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
@RequiredArgsConstructor
public class ProductOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name="productCode", nullable=false)
    private Product product;

    @Column (nullable = false)
    private Long quantity;

    @Column (nullable = false)
    private Long productPrize;

    @ManyToOne
    @JoinColumn(name="name")
    private Campaign campaign;

}
