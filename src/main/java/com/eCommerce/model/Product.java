package com.eCommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank
    private String productName;
    private String image;
    @NotBlank
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany(mappedBy = "product" , cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<CartItem> products = new ArrayList<>();

}
