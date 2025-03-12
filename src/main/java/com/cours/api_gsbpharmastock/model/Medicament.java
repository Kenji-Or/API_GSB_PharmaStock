package com.cours.api_gsbpharmastock.model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "medicaments")
public class Medicament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int quantity;

    @Column(nullable = false)
    private int category;

    @Column(name = "date_expiration")
    private String date_expiration;

    @Column(name = "alerte_stock")
    private int alerte_stock;

}
