package com.rubem.oliota.api2023.pt.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "item")
    private List<CustomerOrder> customerOrders;

    @OneToMany(mappedBy = "item")
    private List<StockMovement> stockMovements;


}