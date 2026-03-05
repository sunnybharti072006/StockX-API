package com.TradeEngine.StockXAPI.model;


import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @Column(nullable = false)
    private BigDecimal balance;


    //@Version is an annotation provided by the Java Persistence API (JPA),
    // and it is used to implement an optimistic locking strategy
    @Version
    private Long version;

}
