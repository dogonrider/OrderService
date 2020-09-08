package com.treez.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="INVENTORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer quantityAvailable;
}
