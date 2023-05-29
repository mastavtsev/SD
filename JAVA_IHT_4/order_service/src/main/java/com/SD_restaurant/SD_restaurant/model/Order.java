package com.SD_restaurant.SD_restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // primary key

    private String orderNumber;
    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Dish> dishesList;
    private String specialDescription;

    private Long userId;
}
