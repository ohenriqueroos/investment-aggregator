package com.investment_aggregator.insvestment_aggregator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_stocks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stock {
    @Id
    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "description")
    private String description;
}
