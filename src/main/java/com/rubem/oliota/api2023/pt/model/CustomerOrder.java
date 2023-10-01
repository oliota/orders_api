package com.rubem.oliota.api2023.pt.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @ManyToOne
    private Item item;

    private boolean completed;

    @ManyToOne
    private UserSystem userSystem;

    @OneToMany(mappedBy = "customerOrder")
    private List<StockMovement> stockMovements;


}
