package com.treez.spring.model;

import com.treez.spring.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="PLACED_ORDER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String customerEmailAddress;

    private Date dateOrderPlaced;

    private OrderStatus orderStatus;

    private Long inventoryId;

    private Integer quantityOfOrder;
}
