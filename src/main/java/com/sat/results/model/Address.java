package com.sat.results.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address_line1;
    private String city;
    private String country;
    private int pincode;
    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private SATResultsModel satResultsModel;
}
